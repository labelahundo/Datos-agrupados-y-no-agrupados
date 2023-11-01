import java.util.Arrays;

public class MMMCalculo {
    private int[] dataset = data.getX();
    private int numeroClases;
    protected int [] puntosMedios;

    public void DiscreteMaths(int[] dataset) {
        this.dataset = dataset;
        this.numeroClases = numeroclases();
        this.puntosMedios=puntomedio();
    }

    public int rango() {
        int max = dataset[0];
        int min = dataset[0];

        for (int dato : dataset) {
            if (dato > max) {
                max = dato;
            }
            if (dato < min) {
                min = dato;
            }
        }

        return max - min;
    }

    public int numeroclases() {
        int n = dataset.length;
        int numeroClases = (int) Math.ceil(1 + 3.322 * Math.log10(n));
        return numeroClases;
    }

    public int amplitudintervalo() {
        int rango = rango();
        int numeroClases = numeroclases();
        int amplitudIntervalo = (int) Math.ceil((double) rango / numeroClases);
        return amplitudIntervalo;
    }

    public int[] frecuenciaAbsoluta() {
        int numeroClases = numeroclases();
        int[] frecuencias = new int[numeroClases];
        int amplitud = amplitudintervalo();
        int valorMinimo = Arrays.stream(dataset).min().getAsInt();

        for (int dato : dataset) {
            int clase = Math.max(0, Math.min((dato - valorMinimo) / amplitud, numeroClases - 1));
            frecuencias[clase]++;
        }

        return frecuencias;
    }

    public int[] puntomedio() {
        int numeroClases = numeroclases();
        int[] puntosMedios = new int[numeroClases];

        int amplitud = amplitudintervalo();
        int valorMinimo = Arrays.stream(dataset).min().getAsInt();

        for (int i = 0; i < numeroClases; i++) {
            int limiteInferior = valorMinimo + i * amplitud;
            int limiteSuperior = limiteInferior + amplitud;
            puntosMedios[i] = (limiteInferior + limiteSuperior) / 2;
        }

        return puntosMedios;
    }

    public int[] frecuenciaAcumulada() {
        int numeroClases = numeroclases();
        int[] frecuenciasAcumuladas = new int[numeroClases];
        int[] frecuencias = frecuenciaAbsoluta();
        int acumulada = 0;

        for (int i = 0; i < numeroClases; i++) {
            acumulada += frecuencias[i];
            frecuenciasAcumuladas[i] = acumulada;
        }

        return frecuenciasAcumuladas;
    }

    public double[] FrecuenciaRelativa() {
        int numeroClases = numeroclases();
        double[] frecuenciasRelativas = new double[numeroClases];
        int[] frecuencias = frecuenciaAbsoluta();
        int totalDatos = dataset.length;

        if (totalDatos > 0) {
            for (int i = 0; i < numeroClases; i++) {
                frecuenciasRelativas[i] = (double) frecuencias[i] / totalDatos;
            }
        } else {
            Arrays.fill(frecuenciasRelativas, 0.0);
        }

        return frecuenciasRelativas;
    }

    public double[] frecuenciaRelativaAcumulada() {
        // Crea un arreglo para almacenar las frecuencias relativas acumuladas
        int numeroClases = numeroclases();
        double[] frecuenciasRelativasAcumuladas = new double[numeroClases];
        double[] frecuenciasRelativas = FrecuenciaRelativa();

        // Calcula la frecuencia relativa acumulada
        double acumulada = 0.0;
        for (int i = 0; i < numeroClases; i++) {
            acumulada += frecuenciasRelativas[i];
            frecuenciasRelativasAcumuladas[i] = acumulada;
        }

        return frecuenciasRelativasAcumuladas;
    }
    public double[] porcentaje() {
        double[] porcentajes = new double[numeroClases];
        double[] frecuenciasRelativas = FrecuenciaRelativa();

        for (int i = 0; i < numeroClases; i++) {
            porcentajes[i] = frecuenciasRelativas[i] * 100;
        }

        return porcentajes;
    }



    public void construirTablaDescriptiva() {
        int numeroClases = numeroclases();
        int[] clases = new int[numeroClases];
        int amplitud = amplitudintervalo();
        int valorMinimo = Arrays.stream(dataset).min().getAsInt();

        int[] frecuencias = frecuenciaAbsoluta();
        int[] puntosMedios = puntomedio();
        int[] frecuenciasAcumuladas = frecuenciaAcumulada();
        double[] frecuenciasRelativas = FrecuenciaRelativa();
        double[] frecuenciasRelativasAcumuladas = frecuenciaRelativaAcumulada();

        System.out.println("Intervalo\tFAbs\t\tPM\t\t\tFAA\t\t\tFR\t\t\tFRAcum\t\tPorcentaje");
        double limiteInferior = valorMinimo;
        for (int i = 0; i < numeroClases; i++) {
            double limiteSuperior = limiteInferior + amplitud;

            double porcentaje = frecuenciasRelativas[i] * 100;

            System.out.printf("%d - %d\t\t%d\t\t\t%d\t\t\t%d\t\t\t%.2f\t\t%.2f\t\t\t%.2f%%\n",
                    (int)limiteInferior, (int)limiteSuperior, frecuencias[i], puntosMedios[i], frecuenciasAcumuladas[i],
                    frecuenciasRelativas[i], frecuenciasRelativasAcumuladas[i], porcentaje);

            limiteInferior = limiteSuperior;
        }
    }
        public int media(int[] dataset) {
            int suma = 0;
            for (int dato : dataset) {
                suma += dato;
            }
            return suma / dataset.length;
        }

        public int moda(int[] dataset) {
            int moda = dataset[0];
            int maxCount = 0;

            for (int i = 0; i < dataset.length; i++) {
                int count = 0;
                for (int j = 0; j < dataset.length; j++) {
                    if (dataset[j] == dataset[i]) {
                        count++;
                    }
                }
                if (count > maxCount) {
                    maxCount = count;
                    moda = dataset[i];
                }
            }
            return moda;
        }

        public int mediana(int[] dataset) {
            Arrays.sort(dataset);
            int n = dataset.length;
            if (n % 2 == 0) {
                int middle1 = dataset[n / 2];
                int middle2 = dataset[n / 2 - 1];
                return (middle1 + middle2) / 2;
            } else {
                return dataset[n / 2];
            }
        }

        public double calcularMediaAgrupada(int[] clases, int[] frecuencias) {
            double suma = 0;
            for (int i = 0; i < numeroClases; i++) {
                double puntoMedio;
                puntoMedio = puntosMedios[i];
                suma += puntoMedio * frecuencias[i];
            }
            return suma / dataset.length;
        }

        public double calcularModaAgrupada(int[] clases, int[] frecuencias) {
            int moda = clases[0];
            int maxFrecuencia = frecuencias[0];

            for (int i = 1; i < numeroClases; i++) {
                if (frecuencias[i] > maxFrecuencia) {
                    moda = clases[i];
                    maxFrecuencia = frecuencias[i];
                }
            }
            return moda;
        }

        public double calcularMedianaAgrupada(int[] clases, int[] frecuencias) {
            int n = dataset.length;
            int mitad = (n + 1) / 2;
            int claseMediana = 0;

            int acumulada = 0;
            for (int i = 0; i < numeroClases; i++) {
                acumulada += frecuencias[i];
                if (acumulada >= mitad) {
                    claseMediana = i;
                    break;
                }
            }

            double Li = clases[claseMediana];
            int fi = frecuencias[claseMediana];
            int nAnt = (claseMediana > 0) ? frecuencias[claseMediana - 1] : 0;
            int Di = amplitudintervalo();
            return Li + ((mitad - nAnt) * Di) / fi;
        }

    }
    