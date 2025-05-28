import java.util.*;

public class DataSplitter {

    public static List<int[]> split(int[] x, int[] y, double trainRatio) {
        int n = x.length;
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < n; i++) indices.add(i);
        Collections.shuffle(indices);

        int trainSize = (int)(n * trainRatio);

        int[] xTrain = new int[trainSize];
        int[] yTrain = new int[trainSize];
        int[] xTest = new int[n - trainSize];
        int[] yTest = new int[n - trainSize];

        for (int i = 0; i < trainSize; i++) {
            xTrain[i] = x[indices.get(i)];
            yTrain[i] = y[indices.get(i)];
        }

        for (int i = trainSize; i < n; i++) {
            xTest[i - trainSize] = x[indices.get(i)];
            yTest[i - trainSize] = y[indices.get(i)];
        }

        return Arrays.asList(xTrain, yTrain, xTest, yTest);
    }
}