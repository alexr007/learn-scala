package hackerrank.d200320_05.stones;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManasaAndStonesApp3_Recursive {

  static Set<Integer> stonesr(int n, int a, int b, Set<Integer> curr) {
    if (n == 0) return curr;
    return stonesr(n-1, a, b,
        curr.stream().flatMap(x -> Stream.of(x+a, x+b))
            .collect(Collectors.toSet())
    );
  }

  static int[] stones(int n, int a, int b) {
    return stonesr(n-1, a, b, new HashSet<Integer>() {{ add(0); }})
        .stream()
        .sorted()
        .mapToInt(x -> x)
        .toArray();
  }

  public static void main(String[] args) {
    System.out.println(Arrays.toString(stones(4, 10, 100)));
  }
}
