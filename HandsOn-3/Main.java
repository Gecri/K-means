import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataSet dataSet = new DataSet();
        int[] x = dataSet.getIndependiente(); // independiente (x)
        int[] y = dataSet.getDependiente();   // dependiente (y)

        double mejorR2 = Double.NEGATIVE_INFINITY;
        String mejorModelo = "";
        SLR mejorSLR = null;
        Cuadratica mejorCuadratica = null;
        Cubico mejorCubico = null;
        Correlacion corr = new Correlacion();

        for (int i = 0; i < 3; i++) {
            List<int[]> split = DataSplitter.split(x, y, 0.7);
            int[] xTrain = split.get(0);
            int[] yTrain = split.get(1);
            int[] xTest = split.get(2);
            int[] yTest = split.get(3);

            // Crear instancias de modelos para esta iteración
            SLR slr = new SLR();
            Cuadratica cuadratica = new Cuadratica();
            Cubico cubico = new Cubico();

            // Entrenar cada modelo con datos de entrenamiento
            slr.entrenar(xTrain, yTrain);
            cuadratica.entrenar(xTrain, yTrain);
            cubico.entrenar(xTrain, yTrain);

            // Predecir con datos de prueba
            int[] predSLR = slr.predecir(xTest);
            int[] predCuadratica = cuadratica.predecir(xTest);
            int[] predCubico = cubico.predecir(xTest);

            // Calcular R2 para cada modelo
            Coeficiente coef = new Coeficiente();
            double r2SLR = coef.calcularR2(yTest, predSLR);
            double r2Cuad = coef.calcularR2(yTest, predCuadratica);
            double r2Cub = coef.calcularR2(yTest, predCubico);

            System.out.printf("Iteración %d:\n", i + 1);
            System.out.printf("  SLR R² = %.4f\n", r2SLR);
            System.out.printf("  Cuadrática R² = %.4f\n", r2Cuad);
            System.out.printf("  Cúbico R² = %.4f\n", r2Cub);

            // Comparar y guardar el mejor modelo (consideramos los 3)
            if (r2SLR > mejorR2) {
                mejorR2 = r2SLR;
                mejorModelo = "SLR";
                mejorSLR = slr;
                mejorCuadratica = null;
                mejorCubico = null;
            }
            if (r2Cuad > mejorR2) {
                mejorR2 = r2Cuad;
                mejorModelo = "Cuadráticaa";
                mejorSLR = null;
                mejorCuadratica = cuadratica;
                mejorCubico = null;
            }
            if (r2Cub > mejorR2) {
                mejorR2 = r2Cub;
                mejorModelo = "Cúbico";
                mejorSLR = null;
                mejorCuadratica = null;
                mejorCubico = cubico;
            }
            // Guardar los modelos de la última iteración para imprimir sus ecuaciones
            if (i == 2) {
                mejorSLR = slr;
                mejorCuadratica = cuadratica;
                mejorCubico = cubico;
            }
        }

        System.out.println("\nEcuaciones finales de todos los modelos (última iteración):");
        System.out.print("SLR: ");
        mejorSLR.imprimirEcuacion();
        System.out.print("Cuadrática: ");
        mejorCuadratica.imprimirEcuacion();
        System.out.print("Cúbica: ");
        mejorCubico.imprimirEcuacion();

        System.out.printf("\n🏆 Mejor modelo encontrado: %s\n", mejorModelo);
        System.out.printf("Con Coeficiente de Determinación R² = %.4f\n", mejorR2);

        System.out.println("\nPredicciones para Batch Sizes conocidos y desconocidos:");
        int[] batchSizes = {32, 64, 128, 256, 512};

        for (int batch : batchSizes) {
            int[] xPred = {batch};
            int yPred = 0;

            if (mejorSLR != null) {
                yPred = mejorSLR.predecir(xPred)[0];
            } else if (mejorCuadratica != null) {
                yPred = mejorCuadratica.predecir(xPred)[0];
            } else if (mejorCubico != null) {
                yPred = mejorCubico.predecir(xPred)[0];
            }

            System.out.printf("Batch Size: %d => Predicción: y = %d\n", batch, yPred);
        }
        double r = corr.calcular(x, y);
        System.out.printf("Correlación de Pearson (r) = %.4f\n", r);
    }
}