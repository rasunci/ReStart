import java.util.Arrays;

class Solution {

    public String rankTeams(String[] votes) {

        // 1 <= votes.length <= 1000
        // 1 <= votes[i].length <= 26
        // .: votes[0] nonempty String
        int n = votes[0].length();

        // votes[i][j] is an English uppercase letter
        int[][] counts = new int[26][n];

        // tally votes
        for (String vote : votes) {
            for (int i = 0; i < n; i++) {
                counts[vote.charAt(i) - 'A'][i]++;
            }
        }

		// Arrays.sort needs Character[] (can't use char[])
        Character[] teams = new Character[n];
        for (int i = 0; i < n; i++) {
            teams[i] = votes[0].charAt(i);
        }

        // apply rank rules
        Arrays.sort(teams, (a, b) -> {
            int[] aCounts = counts[a - 'A'];
            int[] bCounts = counts[b - 'A'];
            for (int i = 0; i < n; i++) {
                if (aCounts[i] != bCounts[i]) {
                    return Integer.compare(bCounts[i], aCounts[i]);
                }
            }
            return Character.compare(a, b);
        });

        StringBuilder sb = new StringBuilder();
        for (Character c : teams) {
            sb.append(c);
        }
        return sb.toString();
    }
}