public class Cubico {
    private int n;
    private int[] x;
    private int[] y;
    private double[] coeficientes;

    public void entrenar(int[] x, int[] y) {
        this.x = x;
        this.y = y;
        this.n = x.length;

        double sumX = 0, sumY = 0, sumX2 = 0, sumXY = 0;
        double sumX3 = 0, sumX4 = 0, sumX5 = 0, sumX6 = 0;
        double sumX2Y = 0, sumX3Y = 0;

        for (int i = 0; i < n; i++) {
            double xi = x[i];
            double yi = y[i];

            sumX += xi;
            sumY += yi;
            sumX2 += xi * xi;
            sumXY += xi * yi;
            sumX3 += xi * xi * xi;
            sumX4 += xi * xi * xi * xi;
            sumX5 += xi * xi * xi * xi * xi;
            sumX6 += xi * xi * xi * xi * xi * xi;
            sumX2Y += xi * xi * yi;
            sumX3Y += xi * xi * xi * yi;
        }

        double[][] A = {
                {n, sumX, sumX2, sumX3},
                {sumX, sumX2, sumX3, sumX4},
                {sumX2, sumX3, sumX4, sumX5},
                {sumX3, sumX4, sumX5, sumX6}
        };

        double[] B = {sumY, sumXY, sumX2Y, sumX3Y};

        coeficientes = resolverSistemaEcuaciones(A, B);
    }

    private double[] resolverSistemaEcuaciones(double[][] A, double[] B) {
        int size = B.length;
        double[] coef = new double[size];

        for (int i = 0; i < size; i++) {
            int max = i;
            for (int j = i + 1; j < size; j++) {
                if (Math.abs(A[j][i]) > Math.abs(A[max][i])) {
                    max = j;
                }
            }

            double[] temp = A[i];
            A[i] = A[max];
            A[max] = temp;

            double t = B[i];
            B[i] = B[max];
            B[max] = t;

            for (int j = i + 1; j < size; j++) {
                double factor = A[j][i] / A[i][i];
                B[j] -= factor * B[i];
                for (int k = i; k < size; k++) {
                    A[j][k] -= factor * A[i][k];
                }
            }
        }

        for (int i = size - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = i + 1; j < size; j++) {
                sum += A[i][j] * coef[j];
            }
            coef[i] = (B[i] - sum) / A[i][i];
        }

        return coef;
    }

    public int[] predecir(int[] xPred) {
        int[] predicciones = new int[xPred.length];
        for (int i = 0; i < xPred.length; i++) {
            double xi = xPred[i];
            double val = coeficientes[0]
                    + coeficientes[1] * xi
                    + coeficientes[2] * xi * xi
                    + coeficientes[3] * xi * xi * xi;
            predicciones[i] = (int) Math.round(val);
        }
        return predicciones;
    }

    public void imprimirEcuacion() {
        if (coeficientes == null) {
            System.out.println("Modelo cúbico no entrenado.");
            return;
        }
        System.out.printf("Ecuación de la regresión cúbica:\ny = %.5f + %.5fx + %.5fx² + %.8fx³\n",
                coeficientes[0], coeficientes[1], coeficientes[2], coeficientes[3]);
    }
}