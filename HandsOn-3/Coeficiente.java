public class Coeficiente {
    // Calcula el Coeficiente de Determinación R^2 dado arrays de valores reales y predichos
    public double calcularR2(int[] yReal, int[] yPredicho) {
        int n = yReal.length;
        double promedioY = 0;
        for (int val : yReal) promedioY += val;
        promedioY /= n;

        double ssRes = 0;
        double ssTot = 0;

        for (int i = 0; i < n; i++) {
            ssRes += Math.pow(yReal[i] - yPredicho[i], 2);
            ssTot += Math.pow(yReal[i] - promedioY, 2);
        }

        return 1 - (ssRes / ssTot);
    }
}