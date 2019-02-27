package cn.demo_test;


class Demo1 {

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 6, 5, 4};
//		revArray(arr);
//		Arrays.sort(arr);							//将乱序数组排序,升序排列
//		System.out.println(Arrays.toString(arr));	//将数组转字符串
        System.out.println(arrToString(arr));
    }

    /**
     * 数组反转  返回值类型void 参数列表,int[]arr
     *
     * @param arr
     */
    public static void revArray(int[] arr) {
        for (int x = 0; x < arr.length / 2; x++) {
            int temp = arr[x];
            arr[x] = arr[arr.length - 1 - x];
            arr[arr.length - 1 - x] = temp;
        }

        for (int x = 0; x < arr.length; x++) {
            System.out.print(arr[x] + " ");
        }
    }


    /**
     * 模拟toString方法将数组转成字符串
     *
     * @param arr
     * @return
     */
    public static String arrToString(int[] arr) {    //{3,2,1,6,5,4};
        //[3, 2, 1, 6, 5, 4]
        String str = "[";                            //arr.length = 6 x的最大值是5
        for (int x = 0; x < arr.length; x++) {
            if (x != arr.length - 1) {
                str = str + arr[x] + ", ";            //str = [3,
            } else {                                    //str = [3, 2,
                str = str + arr[x] + "]";            //str = [3, 2, 1, 6, 5,
            }                                        //str = [3, 2, 1, 6, 5, 4]
        }

        return str;
    }

}
