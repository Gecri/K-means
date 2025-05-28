public class CalculosRegresion {
    private int[] x;
    private int[] y;
    public int n;

    private int sumX;
    private int sumY;
    private int sumX2;
    private int sumY2;
    private int sumXY;

    public CalculosRegresion() {
        // Constructor vacío
    }

    public void setDatos(int[] x, int[] y) {
        this.x = x;
        this.y = y;
        this.n = x.length;

        sumX = 0;
        sumY = 0;
        sumX2 = 0;
        sumY2 = 0;
        sumXY = 0;

        for (int i = 0; i < n; i++) {
            sumX += x[i];
            sumY += y[i];
            sumX2 += x[i] * x[i];
            sumY2 += y[i] * y[i];
            sumXY += x[i] * y[i];
        }
    }

    public int sumX() { return sumX; }
    public int sumY() { return sumY; }
    public int sumX2() { return sumX2; }
    public int sumY2() { return sumY2; }
    public int sumXY() { return sumXY; }
}