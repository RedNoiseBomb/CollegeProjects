package main.java;

public class Domain {

    int[] vals;


    public Domain(int[] vals) {
        this.vals = vals;
    }


    public Domain(Domain d2) {
        vals = new int[d2.vals.length];
        for(int i = 0; i < vals.length; i++)
            this.vals[i] = d2.vals[i];
    }

    public void delete(int index) {
        int[] newArr = new int[vals.length - 1]; // new array with one less element

        int j = 0; // index for new array
        for (int i = 0; i < vals.length; i++) {
            if (i != index) {
                newArr[j] = vals[i]; // copy element to new array
                j++; // increment index for new array
            }
        }

        this.vals = newArr;
    }

    /**
     * @return
     */
    public String toString() {
        String result  = "{";
        for (int i = 0; i < vals.length; i++)
            result += vals[i];
        result += "}";
        return result;
    }

    /**
     * @return
     */
    public Domain[] split(Domain d) {
        Domain[] splitArr = new Domain[2];
        int arr1[];
        int arr2[];
        
        if (d.vals.length % 2 == 0) {
            arr1 = new int[d.vals.length / 2];
            arr2 = new int[d.vals.length / 2];
        }
        else {
            arr1 = new int[d.vals.length / 2];
            arr2 = new int[d.vals.length / 2 + 1];
        }

        int i = 0;
        for (i = 0; i < d.vals.length / 2; i++) {
            arr1[i] = d.vals[i];
        }
        Domain d1 = new Domain(arr1);
        splitArr[0] = d1;

        for (int j = 0; i < d.vals.length; i++) {
            arr2[j++] = d.vals[i];
        }
        Domain d2 = new Domain(arr2);
        splitArr[1] = d2;

        return splitArr;
    }

    /**
     * @return
     */
    private boolean isEmpty() {
        if (this.vals.length == 0) {
            return true;
        }
        else
            return false;
    }

    /**
     * @return
     */
    private boolean equals(Domain d2) {
        if (this == d2) {
            return true;
        }
        else
            return false;
    }

    /**
     * @return
     */
    private boolean isReducedToOnlyOneValue() {
        if (this.vals.length == 1) {
            return true;
        }
        else
            return false;
    }

    public Domain largestDomain(Domain[] arrOfDoms) {
        int[] initArr = new int[0];
        Domain largestDom = new Domain(initArr);
        for (int i = 0; i < arrOfDoms.length; i++) {
            if (arrOfDoms[i].vals.length > largestDom.vals.length) {
                largestDom = arrOfDoms[i];
            }
        }
        return largestDom;
    }

}
