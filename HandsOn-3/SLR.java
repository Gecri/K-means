public class SLR {
    private CalculosRegresion calculosRegresion;

    public SLR() {
        calculosRegresion = new CalculosRegresion();
    }

    public void entrenar(int[] x, int[] y) {
        calculosRegresion.setDatos(x, y);
    }

    public float B1() {
        float numerator = (calculosRegresion.n * calculosRegresion.sumXY()) -
                (calculosRegresion.sumX() * calculosRegresion.sumY());
        float denominator = (calculosRegresion.n * calculosRegresion.sumX2()) -
                (calculosRegresion.sumX() * calculosRegresion.sumX());
        return numerator / denominator;
    }

    public float B0() {
        return (calculosRegresion.sumY() - B1() * calculosRegresion.sumX()) / calculosRegresion.n;
    }

    public int[] predecir(int[] x) {
        int[] predicciones = new int[x.length];
        float b0 = B0();
        float b1 = B1();
        for (int i = 0; i < x.length; i++) {
            predicciones[i] = Math.round(b0 + b1 * x[i]);
        }
        return predicciones;
    }

    public void imprimirEcuacion() {
        System.out.println("Ecuación de la regresión lineal: y = " + B0() + " + " + B1() + "x");
    }
}