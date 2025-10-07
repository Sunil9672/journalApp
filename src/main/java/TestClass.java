import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TestClass {

    //Given an integer array where each element is at most k positions away from its sorted position, sort the array in a non-decreasing order.
    //
    //Example:
    //Input: nums = [5, 1, 9, 4, 7, 10], k = 2
//    [1, 4, 5, 7, 9, 10]
    //Output: [1, 4, 5, 7, 9, 10]
    //O(k)
    //O(nlog(k))

    public static void main(String[] args) {
        int[] arr = new int[]{5, 1, 9, 4, 7, 10};
        int k = 11000;
        System.out.println(driver(arr, k));
    }

    public static List<Integer> driver(int[] arr, int k){
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        List<Integer> result = new ArrayList<>();

        for(int num: arr){
            if(pq.size()<=k){
                if(pq.size()==k && pq.){

                }
                pq.offer(num);
            } else {
                result.add(pq.poll());
                pq.offer(num);
            }
        }

        while(!pq.isEmpty()){
            result.add(pq.poll());
        }

        return result;
    }
}
