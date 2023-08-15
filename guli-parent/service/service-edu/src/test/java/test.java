import org.junit.Test;

/**
 * @author kevin
 * @version 1.0
 * @date 2023-07-20 21:33
 */
public class test {
    @Test
    public void test1(){
        //乘法表
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <=i ; j++) {
                System.out.print(j+"*"+i+"="+i*j+"\t");
            }
            System.out.println();
        }
    }

    //冒泡排序
    @Test
    public void test2(){
        int[] arr = {1, 3, 2, 5, 4};
        int temp = 0;
        for (int i = 0; i < arr.length-1; i++) {
            boolean flag = false;
            for (int j = 0; j < arr.length-1-i; j++) {
                if(arr[j]>arr[j+1]){
                    flag = true;
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
            if(!flag){
                break;
            }
        }
        for (int i : arr) {
            System.out.println(i);
        }
    }

    //最小二叉树
    @Test
    public void test3(){
        int[] arr = {1, 3, 2, 5, 4};
        int temp = 0;
        for (int i = 0; i < arr.length-1; i++) {
            boolean flag = false;
            for (int j = 0; j < arr.length-1-i; j++) {
                if(arr[j]>arr[j+1]){
                    flag = true;
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
            if(!flag){
                break;
            }
        }
        for (int i : arr) {
            System.out.println(i);
        }
    }

    //动态规划
    @Test
    public void test4(){
        int[] arr = {1, 3, 2, 5, 4};
        int temp = 0;
        for (int i = 0; i < arr.length-1; i++) {
            boolean flag = false;
            for (int j = 0; j < arr.length-1-i; j++) {
                if(arr[j]>arr[j+1]){
                    flag = true;
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
            if(!flag){
                break;
            }
        }
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
