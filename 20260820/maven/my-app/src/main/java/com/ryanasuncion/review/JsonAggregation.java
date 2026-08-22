package com.ryanasuncion.review;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParser.Event;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static jakarta.json.stream.JsonParser.Event.*;


public class JsonAggregation {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final LocalDate REPORT_DATE = LocalDate.parse("2025-10-09", DATE_TIME_FORMATTER);

    // 30 calendar-day inclusive window: 2025-09-10 through 2025-10-09.
    private static final LocalDate WINDOW_START = REPORT_DATE.minusDays(29);

    private final Set<String> seenTransactionIds = new HashSet<>();

    private final Map<String, String> userNames = new HashMap<>();
    private final Map<String, UserAggregate> aggregates = new HashMap<>();

    private final int TOP_USER_COUNT = 3;

    record Transaction(String id, String userId, LocalDate timestamp, BigDecimal amount, String category) {
    }

    static class UserAggregate {
        final String userId;
        final String name;
        BigDecimal totalUsd = BigDecimal.ZERO;
        Map<String, BigDecimal> byCategory = new HashMap<>();

        UserAggregate(String userId, String name) {
            this.userId = userId;
            this.name = name;
        }

        void add(Transaction transaction) {
            totalUsd = totalUsd.add(transaction.amount);
            byCategory.merge(transaction.category, transaction.amount, BigDecimal::add);
        }
    }

    record CategoryReport(String category, BigDecimal usd) {
    }

    record UserReport(String id, String name, BigDecimal totalUsd, List<CategoryReport> byCategory) {
    }

    record User(String id, String name) {
    }

    public JsonAggregation(InputStream inputStream) {
        try (JsonParser parser = Json.createParser(inputStream)) {
            if (!isValidStart(parser, "users")) return;
            while (parser.hasNext()) {
                Event event = parser.next();
                if (event == END_ARRAY) {
                    return;
                }
                if (event != START_OBJECT) {
                    throw new IllegalArgumentException("Expected users object, got " + event);
                }
                User user = toUser(parser);
                userNames.put(user.id, user.name);
            }
        }
    }

    public void process(InputStream inputStream) {
        try (JsonParser parser = Json.createParser(inputStream)) {
            if (!isValidStart(parser, "transactions")) return;
            while (parser.hasNext()) {
                Event event = parser.next();
                if (event == END_ARRAY) {
                    return;
                }
                if (event != START_OBJECT) {
                    throw new IllegalArgumentException("Expected transaction object, got " + event);
                }
                Transaction transaction = toTransaction(parser);
                processTransaction(transaction);
            }
        }
    }

    private void processTransaction(Transaction transaction) {
        // first occurrence wins
        if (!seenTransactionIds.add(transaction.id)) {
            return;
        }
        // invalid user
        if (transaction.userId == null || transaction.userId.isBlank()) {
            return;
        }
        // invalid amount
        if (transaction.amount.signum() <= 0) {
            return;
        }
        // filter date
        if (transaction.timestamp.isBefore(WINDOW_START) || transaction.timestamp.isAfter(REPORT_DATE)) {
            return;
        }

        UserAggregate userAggregate = aggregates.computeIfAbsent(transaction.userId, k -> new UserAggregate(transaction.userId(), userNames.get(transaction.userId())));

        userAggregate.add(transaction);
    }

    private UserReport toReport(UserAggregate aggregate) {
        List<CategoryReport> categories = aggregate.byCategory.entrySet().stream().map(
                entry -> new CategoryReport(
                            entry.getKey(),
                            entry.getValue().setScale(2, RoundingMode.HALF_UP)))
                            .sorted(Comparator.comparing(CategoryReport::usd).reversed()).toList();
        return new UserReport(aggregate.userId, aggregate.name, aggregate.totalUsd.setScale(2, RoundingMode.HALF_UP), categories);
    }

    private List<UserReport> buildUserReports() {
        return aggregates.values().stream().map(this::toReport)
                .sorted(Comparator.comparing(UserReport::totalUsd).reversed().thenComparing(UserReport::name)).toList();
    }

    public JsonObject generateReport() {
        List<UserReport> users = buildUserReports();
        JsonArrayBuilder usersJson = Json.createArrayBuilder();
        for (UserReport user : users) {
            JsonArrayBuilder categoriesJson = Json.createArrayBuilder();
            for (CategoryReport category : user.byCategory()) {
                categoriesJson.add(Json.createObjectBuilder().add("category", category.category()).add("usd", category.usd()));
            }
            usersJson.add(
                    Json.createObjectBuilder()
                            .add("user_id", user.id)
                            .add("name", user.name)
                            .add("total_usd", user.totalUsd)
                            .add("by_category", categoriesJson));
        }
        JsonArrayBuilder topUsersJson = Json.createArrayBuilder();
        for (UserReport user : users.stream().limit(TOP_USER_COUNT).toList()) {
            topUsersJson.add(Json.createObjectBuilder()
                    .add("user_id", user.id)
                    .add("name", user.name)
                    .add("total_usd", user.totalUsd));
        }
        return Json.createObjectBuilder()
                .add("generated_at", "2025-10-09T00:00:00Z")
                .add("users", usersJson)
                .add("top_users", topUsersJson).build();
    }

    private User toUser(JsonParser parser) {
        String id = null;
        String name = null;
        while (parser.hasNext()) {
            Event event = parser.next();
            if (event == END_OBJECT) {
                break;
            }
            if (event != KEY_NAME) {
                throw new IllegalArgumentException("Expected field name, instead found " + event);
            }
            String field = parser.getString();
            switch (field) {
                case "id":
                    parser.next();
                    id = parser.getString();
                    break;
                case "name":
                    parser.next();
                    name = parser.getString();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field " + field);
            }
        }
        return new User(id, name);
    }

    private Transaction toTransaction(JsonParser parser) {
        String id = null;
        String userId = null;
        LocalDate timestamp = null;
        BigDecimal amount = null;
        String category = null;
        while (parser.hasNext()) {
            Event event = parser.next();
            if (event == END_OBJECT) {
                break;
            }
            if (event != KEY_NAME) {
                throw new IllegalArgumentException("Expected field name, instead found " + event);
            }
            String field = parser.getString();
            switch (field) {
                case "id":
                    parser.next();
                    id = parser.getString();
                    break;
                case "user_id":
                    parser.next();
                    userId = parser.getString();
                    break;
                case "ts":
                    parser.next();
                    timestamp = LocalDate.parse(parser.getString(), DATE_TIME_FORMATTER);
                    break;
                case "amount":
                    parser.next();
                    amount = parser.getBigDecimal();
                    break;
                case "category":
                    parser.next();
                    category = parser.getString();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field " + field);
            }
        }
        return new Transaction(id, userId, timestamp, amount, category);
    }

    private boolean isValidStart(JsonParser parser, String field) {
        // Validate transactions file adheres to form { "transactions" : [ ...
        return require(parser, START_OBJECT) && require(parser, KEY_NAME, field) && require(parser, START_ARRAY);
    }

    private boolean require(JsonParser parser, Event event) {
        return parser.hasNext() && parser.next() == event;
    }

    private boolean require(JsonParser parser, Event event, String s) {
        return require(parser, event) && parser.getString().equals(s);
    }
}
