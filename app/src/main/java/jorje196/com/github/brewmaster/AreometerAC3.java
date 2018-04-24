package jorje196.com.github.brewmaster;

/**
 * Created by User on 16.03.2018.
 * Класс обеспечивает обработку данных, полученных с ареометра АС-3 и
 * предоставляет информацию о нем для отображения в интерфейсе пользователя
 *
 */

    class AreometerAC3 extends Hydrometer{
    // Пределы измерений
    static private final int T_MIN = 0;
    static private final int T_MAX = 30;
    static private final double GRAVITY_MAX = 25;
    static private final double GRAVITY_MIN = 0;
    // Параметры характеристики прибора
    static private final int BASE_TEMPERATURE = 20;
    static private final double T_CORRECTION_CONST = 0.05;
    static private final double INFLACTION_POINT = 6.5;

    static private final String NAME = "АС-3";
    static private final String UNIT = "%";

    double getAlcoholPercent(int beginningWortT, double beginingWortG,
                                    int bottlingWortT, double bottlingWortG) {
        double res;
        // Проверка корректности параметров и , в случае выхода за пределы измерения
        // прибора, замена на ближайшие допустимые. Это допустимо, так как погрешность
        // будет небольшой, а нуждающиеся в точности могут проверять значения перед
        // обращением к методу самостоятельно .
        beginningWortT = tValidationMax(beginningWortT) ? beginningWortT : T_MAX;
        beginningWortT = tValidationMin(beginningWortT) ? beginningWortT : T_MIN;
        bottlingWortT = tValidationMax(bottlingWortT) ? bottlingWortT : T_MAX;
        bottlingWortT = tValidationMin(bottlingWortT) ? bottlingWortT : T_MIN;
        beginingWortG = gValidationMax(beginingWortG) ? beginingWortG : GRAVITY_MAX;
        beginingWortG = gValidationMin(beginingWortG) ? beginingWortG : GRAVITY_MIN;
        bottlingWortG = gValidationMax(bottlingWortG) ? bottlingWortG : GRAVITY_MAX;
        bottlingWortG = gValidationMin(bottlingWortG) ? bottlingWortG : GRAVITY_MIN;

        res = getAlcPotential(temperatureCorrection(beginningWortT, beginingWortG))
                - getAlcPotential(temperatureCorrection(bottlingWortT, bottlingWortG));
        res = Math.rint(res * 100) / 100;

        return res;
    }

    // Проверка корректности параметров;
    static boolean tValidation(int temperature) {

        return temperature >= T_MIN && temperature <= T_MAX;
    }

    private static boolean tValidationMin(int temperature) {
        return temperature >= T_MIN;
    }

    private static boolean tValidationMax(int temperature) {
        return temperature <= T_MAX;
    }

    static boolean gValidation(double gravity) {
        return gravity >= GRAVITY_MIN && gravity <= GRAVITY_MAX;
    }

    private static boolean gValidationMin(double gravity) {
        return gravity >= GRAVITY_MIN;
    }

    private static boolean gValidationMax(double gravity) {
        return gravity <= GRAVITY_MAX;
    }

    static double temperatureCorrection(int temperature, double gravity) {
        //CharSequence s;

        int deltaT = temperature - BASE_TEMPERATURE;
        double resGravity = gravity;
        double correction = deltaT * T_CORRECTION_CONST * 100;
        resGravity += (gravity > INFLACTION_POINT && deltaT < 0 ? Math.ceil(correction) : Math.floor(correction)) / 100;
        return resGravity;
    }

    static String getInfoText() {
        return NAME + "\n в" + UNIT;
    }

    // Метод возвращает потенциальное содержание спирта в зависимости от содержания сахаридов
    // результат в процентах от объёма
    static double getAlcPotential(double gravity) {
        final double[][] DA =   // массив диапазонов
                {{0.5, 9.5}, {10.25, 13.25}, {14, 15}, {15.75, 18.75}, {19.5, 22.5}, {23.25, 25}};
        // Считаем массив диапазонов проверенным, при его определении outside добавить проверку
        int i = 0, j = 0, f = 0, k;
        int kLow = 0, kHigh = DA.length * 2;
        double percentAlc;
        if (gravity < DA[0][0]) gravity = DA[0][0];
        // Заложена некоторая избыточность на случай изменения числа диапазонов
        while (kLow < kHigh - 1) {
            k = (kLow + kHigh) / 2;
            j = k % 2;
            i = k / 2;
            if (gravity > DA[i][j]) {
                kLow = k;
                f = 1;
            } else {
                kHigh = k;
                f = 0;
            }
        }
        j = j == f ? 1 : 0;
        i = j == 1 ? i - j + f : i;
        percentAlc = j == 0 ? getAlc(i, gravity) : getAlc(i, DA[i][j]) + (gravity - DA[i][j]) *
                (getAlc(i + 1, DA[i + 1][j - 1]) - getAlc(i, DA[i][j])) / (DA[i + 1][j - 1] - DA[i][j]);
        return percentAlc;
    }

    // Метод возвращает содержание Alc при заданных плотности и участке характеристики АС-3
    static double getAlc(int i, double gravity) {
        final double RANGE_CORRECTION_CONST = 0.25;
        final double BASE_CONST = -0.5;
        int j = i / 2;
        return (gravity - BASE_CONST + i * RANGE_CORRECTION_CONST) / 2;
    }

    // Метод возвращает содержание Alc по значеним параметров Original & Finil (старт и розлив)
    //static double getAclContent()
}