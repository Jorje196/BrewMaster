package jorje196.com.github.brewmaster;

// Для привязки к ресурсам и ?
//import static jorje196.com.github.brewmaster.MainBeerActivity.cnt;

/**
 * Created by User on 05.01.2018.
 * Класс процесса варки, запись новой информации о варках возможна
 * только через него, он же контролирует корректность данных .
 */

public class BrewProcess {
    //private static Context context;
    //static Calendar calendar;
    //static Resources resources;
    // Константы
    static final int NUL_TEMPERATURE = -273;
    static final int HOUR_IN_MLS = 3600000;
    static final int DAY_IN_HRS = 24;
    private final String EMPTY_STRING = "";

    // Свойства объекта
        // Ингредиенты
    // Наименование и бренд охмеленного экстракта
    private String nameMaltExt;
    void setNameMaltExt(String nameMaltExt) {
        this.nameMaltExt = nameMaltExt;
    }
    String getNameMaltExt() {
        return nameMaltExt;
    }
    private String brandMaltExt;
    String getBrandMaltExt() {
        return brandMaltExt;
    }
    void setBrandMaltExt(String brandMaltExt) {
        this.brandMaltExt = brandMaltExt;
    }
    private int idNameMaltExt;
    int getIdNameMaltExt() {
        return idNameMaltExt;
    }
    void setIdNameMaltExt(int idNameMaltExt) {
        this.idNameMaltExt = idNameMaltExt;
    }

    //
    private double weightMaltExt;               // Количество экстракта, кг
    double getWeightMaltExt() {        return weightMaltExt;    }
    void setWeightMaltExt(double weightMaltExt) {        this.weightMaltExt = weightMaltExt;    }

    // Про горечь и цвет
    private int bitterMaltExt;
    public int getBitterMaltExt() {
        return bitterMaltExt;
    }
    private void setBitterMaltExt(int bitterMaltExt) {
        this.bitterMaltExt = bitterMaltExt;
    }
    private int colorMaltExt;
    public int getColorMaltExt() {
        return colorMaltExt;
    }
    private void setColorMaltExt(int colorMaltExt) {
        this.colorMaltExt = colorMaltExt;
    }


    private double weightDextrosa;                      // Кол-во декстрозы, граммы
    double getWeightDextrosa() {
        return weightDextrosa;
    }
    void setWeightDextrosa(double weightDextrosa) {
        this.weightDextrosa = weightDextrosa;
    }
    private double weightSugar;                         // Кол-во сахара, граммы
    double getWeightSugar() {
        return weightSugar;
    }
    void setWeightSugar(double weightSugar) {
        this.weightSugar = weightSugar;
    }
    private double weightEnhancer;                      // Кол-во усилителя, граммы
    double getWeightEnhancer() {
        return weightEnhancer;
    }
    void setWeightEnhancer(double weightEnhancer) {
        this.weightEnhancer = weightEnhancer;
    }
    private double weightThickener;                      // Кол-во загустителя, граммы
    double getWeightThickener() {
        return weightThickener;
    }
    void setWeightThickener(double weightThickener) {
        this.weightThickener = weightThickener;
    }
    private double weightMaltExtCan;    // Вес банки с охмел солод экстрактом, кг
    double getWeightMaltExtCan() {
        return weightMaltExtCan;
    }
    void setWeightMaltExtCan(double weightMaltExtCan) {
        this.weightMaltExtCan = weightMaltExtCan;
    }
        // Возвращает true если праймер не составлен или некорректен
    boolean isPrimerEmpty(){
        double[] list = {getWeightDextrosa(), getWeightSugar(),
                getWeightEnhancer(), getWeightThickener()};
        return (primerValidation(list) != 0);
    }

    // Количество компонентов в праймере (максимально возможное, для меню и контролей
    static final int PRIMER_NUMBER_OF_COMPONENTS = 4;  // может в базовый контракт перенести ?

    // Метод проверяет корректность праймера, возвращает :
    static final int PRIMER_OK = 0;  // Ok;
    static final int PRIMER_LACK = 1; // = недобор, недостаток
    static final int PRIMER_OVERAGE = 2; // = избыток, излишек  || по количеству
    static final int PRIMER_TYPO = 4; // опечатка, ошибки набора (типа вес < 0;
    static final int PRIMER_THICK_OVERAGE = 8; // = перебор с загустителем;
       // Позже можно добавить проверки на совместимость, можно и наборы побитно
    static final int PRIMER_COMPAUND = 16;  // замес декстрозы с сахаром
    static private final double PRIMER_MAX_WEIGHT = 1700; // вес в граммах, todo заменить на вес банки из базы

    static private final int WORT_MIN_TEMPERATURE = 18;
    static private final int WORT_MAX_TEMPETATURE = 32;
    static private final int OFFSET_MIN_TEMPERATURE = 5;
    static private final int OFFSET_MAX_TMPERATURE =5;

    int primerValidation(double[] list){

        // dextrosa, sugar, enhancer, thick - последовательность размещения параметров в списке
        // можно формализовать для N параметров, сделать когда появится ещё один
        double dextrosa, sugar, enhancer, thick;
        dextrosa = list[0]; sugar = list[1]; enhancer = list[2]; thick = list[3];

        if(dextrosa<0 || sugar<0 || enhancer<0 ||
                thick<0) return PRIMER_TYPO;
        double x;
        x = dextrosa + sugar + enhancer;
        if(x > 0.75*PRIMER_MAX_WEIGHT) return PRIMER_OVERAGE;
        if(x < 0.41*PRIMER_MAX_WEIGHT) return PRIMER_LACK;
        if(thick > 0.18*PRIMER_MAX_WEIGHT) return PRIMER_THICK_OVERAGE;
        if (dextrosa > 0 && sugar > 0) return PRIMER_COMPAUND;
        return PRIMER_OK;
    }

    // ************* Начало процесса бржения
    private int beginingDateTimeHrs, bottledDateTimeHrs;
        // Возвращает long для установки календаря
    long getBeginingDateTimeMls() {
        return ((long)beginingDateTimeHrs)*HOUR_IN_MLS;
    }
        // Возвращает int часы от Начала времен
        int getBeginingDateTimeHrs() {
        return beginingDateTimeHrs;
    }
        // Сохр. int часов от Начала вр. из long mls календаря
        void setBeginingDateTimeHrs(long dateTimeMls) {
        long x = dateTimeMls/HOUR_IN_MLS;
        this.beginingDateTimeHrs = (int) x;
    }
    private void setBeginingDateTimeHrs(int dateTimeHrs){
        this.beginingDateTimeHrs = dateTimeHrs;
    }

    // Дата начала процесса, сохраняем строки, много места не займут, дешевле чем обработка
    private String beginningDate;
    public String getBeginningDate() {
        return beginningDate;
    }
    void setBeginningDate(String startDate) {
        this.beginningDate = startDate;
    }

          // Время -//-, опционально
    public String getBeginningTime() {
        return Integer.toString(beginingDateTimeHrs%24);
    }

    // Начальная и конечная температуры сусла, крайние значения
    private int beginningWortTempereture = NUL_TEMPERATURE;
    int getBeginningWortTempereture() {
        return beginningWortTempereture;
    }
    void setBeginningWortTempereture(int beginningWortTempereture) {
        this.beginningWortTempereture = beginningWortTempereture;
    }
    private int bottlingWortTemperature = NUL_TEMPERATURE;
    int getBottlingWortTemperature() {
        return bottlingWortTemperature;
    }
    void setBottlingWortTemperature(int bottlingWortTemperature) {
        this.bottlingWortTemperature = bottlingWortTemperature;
    }
    private int minWortTemperature = WORT_MIN_TEMPERATURE;
    int getMinWortTemperature() {
        return minWortTemperature;
    }
    private void setMinWortTemperature(int minWortTemperature) {
        this.minWortTemperature = minWortTemperature;
    }
    private int maxWortTemperature = WORT_MAX_TEMPETATURE;
    int getMaxWortTemperature() {
        return maxWortTemperature;
    }
    private void setMaxWortTemperature(int maxWortTemperature) {
        this.maxWortTemperature = maxWortTemperature;
    }
    private int optimumStartWortTemperature;
    int getOptimumStartWortTemperature(){
        return optimumStartWortTemperature;
    }
    private void setOptimumStartWortTemperature(int optimumStartWortTemperature) {
        this.optimumStartWortTemperature = optimumStartWortTemperature;
    }
    private int ambientTemperature = NUL_TEMPERATURE;
    int getAmbientTemperature(){
        return ambientTemperature;
    }
    void setAmbientTemperature(int ambientTemperature){
        this.ambientTemperature = ambientTemperature;
    }
    int getOptimumAmbientTemperature(){
        int t = (this.getMinWortTemperature()+ this.getMaxWortTemperature())/2 - 3;
        return t;
    }
    int getMinAmbientTemperature(){
        return getMinWortTemperature() - OFFSET_MIN_TEMPERATURE;
    }
    int getMaxAmbientTemperature(){
        return getMaxWortTemperature() + OFFSET_MAX_TMPERATURE;
    }

    // Начальная и конечная плотность сусла (или содержание сахаридов, в
    // зависимости от применяемого метода и устройства измерения ).
    private double originalWortGravity;
    double getOriginalWortGravity() {
        return originalWortGravity;
    }
    void setOriginalWortGravity(double originalWortGravity) {
        this.originalWortGravity = originalWortGravity;
    }
    private double finalWortGravity;
    double getFinalWortGravity() {
        return finalWortGravity;
    }
    void setFinalWortGravity(double finalWortGravity) {
        this.finalWortGravity = finalWortGravity;
    }
    final private double MIN_ORIGINAL_WORT_GRAVITY = 7;
    double getMinOriginalWortGravity() {
        return MIN_ORIGINAL_WORT_GRAVITY;
    }
    final private double MAX_ORIGINAL_WORT_GRAVITY = 15;
    double getMaxOriginalWortGravity(){
        return MAX_ORIGINAL_WORT_GRAVITY;
    }
    final private double MIN_FINAL_WORT_GRAVITY = 0;
    double getMinFinalWortGravity() {
        return MIN_FINAL_WORT_GRAVITY;
    }

    final private double MAX_FINAL_WORT_GRAVITY = 5;
    double getMaxFinalWortGravity() {
        return MAX_FINAL_WORT_GRAVITY;
    }

    final private double STEP_WORT_GRAVITY = 0.5;
    double getStepWortGravity(){
        return STEP_WORT_GRAVITY;
    }


    // ***************** Розлив
    private final int FERMENTATION_DURATION_MIN = 120; // минимальная, в часах
    int getMinFermentationDurationHrs(){
        return FERMENTATION_DURATION_MIN;
    }
    int getMaxFermentationDurationHrs() {
        return FERMENTATION_DURATION_MIN *2;
    }
    private int durationOfFermentationHrs;  // достаточно хранить в базе это todo
    public int getDurationOfFermentationHrs() {
        return durationOfFermentationHrs;
    }
    long getDurationOfFermentationMls() {
        return ((long)durationOfFermentationHrs) * HOUR_IN_MLS ;
    }
    private void setDurationOfFermentationHrs(int durationOfFermentationHrs) {
        this.durationOfFermentationHrs = durationOfFermentationHrs;
    }
    void setDurationOfFermentationMls(long durationOfFermentationMls){
        this.durationOfFermentationHrs = (int) durationOfFermentationMls / HOUR_IN_MLS;
    }
    public long getBottlingDate(){
        return getBeginingDateTimeMls()+ getDurationOfFermentationMls();
    }
    private double volumeRecomend; // Рекомендуемый объем сусла, литры
    double getVolumeRecomend() {
        return volumeRecomend;
    }
    private void setVolumeRecomend(double volumeRecomend) {
        this.volumeRecomend = volumeRecomend;
    }
    private double volumeActual;  // Фактический (результат розлива) объем пива, литры
    double getVolumeActual() {
        return volumeActual;
    }
    boolean setVolumeActual(double volumeActual) {
        this.volumeActual = volumeActual;
        return volumeActual > getVolumeRecomend()*0.8 && volumeActual < getVolumeRecomend()*1.2;
    }
    private final double CARBONATION_ALC_MAX = 0.5; // 8г сахарида на 1 литр
    private double alcoholContent;  // Содержание Alc в напитке после завершения карбонизации
    double getAlcoholContent() {
        return alcoholContent;
    }
    void setAlcoholContent(double alcoholContent) {
        this.alcoholContent = alcoholContent;
    }

    private int processStage;       // Этап процесса (от 0 до 2-3 лет)
    private int processStageTime;   // и время с начала этапа в днях
    int getProcessStage() {
        return processStage;
    }
    int getProcessStageTime(){
        return processStageTime;
    }

    // public void setProcessStage(int processStage) { this.processStage = processStage; }
    // *********************** Карбонизация и далее со всеми остановками

    int carbonizaitionPeriod;   // Время на карбонизацию (втор. ферментация), дни
    private final double CARBONATION_ALC = 0.5;
    private final int PARAMETER_MISSING = -1; // Признак отсутствия параметра (за ненадобностью)
    private final int CARBONATION_TIME_MIN = 7; // Минимальная длительность карбонизации;
    private final int CARBONATION_TIME_MAX = 12; // Максимальная продолжительность ... ;
    private final int CARBONATION_TIME_OPT = 10; // Оптимальная продолжительность ... ;
    private final int MATURATION_TIME_MIN = 14;  // Минимальная длительность дозревания;
    private final int MATURATION_TIME_MAX = 60;  // Максимальная длительность ... ;
    private final int AGEING_TIME_MIN = 1;       // Выдержка ... ;
    private final int AGEING_TIME_MAX = 1095;    // Далее уже Best before
    private final int FERMENTATION_STAGE = 0;        // Этапы процесса
    private final int CARBONATION_STAGE = 1;
    private final int MATURATION_STAGE = 2;
    private final int AGEING_STAGE = 3;
    private final int OVERTIME_STAGE =4;
    private final int PROGRESS_MAX = 100;
    private final int[][] PROCESS_STAGES = {
        {CARBONATION_STAGE, CARBONATION_TIME_MIN, CARBONATION_TIME_OPT, CARBONATION_TIME_MAX},
        {MATURATION_STAGE, MATURATION_TIME_MIN, PARAMETER_MISSING, MATURATION_TIME_MAX},
        {AGEING_STAGE, AGEING_TIME_MIN, AGEING_TIME_MAX, AGEING_TIME_MAX}};

    int maturationPeriod;    // Период дозревания (заверш. втор. фермент), дни (от 2 до 12 недель)
    int ageingPeriod;      // Период выдержки, созревания, дни (чем темнее, тем больше)
    int bestBefore;        // Измеряется в днях

    /* Метод возвращает номер строки текста в массиве строк названий этапов (он же номер этапа),
    первичный и вторичный прогрессы этапа процесса в %, логический признак ч/л flg */
    // todo добавить функцию коррекции времени выдержки от цвета и горькости
    int getProcessProgressFirst(){
        int j, i = getProcessStage();
        j = i == OVERTIME_STAGE ?
                PROGRESS_MAX : PROGRESS_MAX * getProcessStageTime() / PROCESS_STAGES[i-1][3];
        return i == OVERTIME_STAGE ?
                PROGRESS_MAX : PROGRESS_MAX * getProcessStageTime() / PROCESS_STAGES[i-1][3];
    }
    int getProcessProgressSecond(){
        int j,k,i = getProcessStage();
        j = PROCESS_STAGES[i-1][1];
        k = PROCESS_STAGES[i-1][3];
        int timeRel;
        timeRel = PROGRESS_MAX * getProcessStageTime()/ PROCESS_STAGES[i-1][1];
        j = i == OVERTIME_STAGE || timeRel > PROGRESS_MAX ? PROGRESS_MAX : timeRel;
         return  i == OVERTIME_STAGE || timeRel > PROGRESS_MAX ? PROGRESS_MAX : timeRel;
    }
    void calcProcessStageAndTime(){
        this.calcProcessStageAndTime(System.currentTimeMillis());
    }
    void calcProcessStageAndTime(long dateAndTime){ // Может понадобиться outside
        int time, num = 0, j,k;
        time = (int)((dateAndTime - this.getBeginingDateTimeMls() -  // todo проверить округление
                this.getDurationOfFermentationMls()) / DAY_IN_HRS / HOUR_IN_MLS);
        for (int i = 0; i < PROCESS_STAGES.length; i++){
            j = PROCESS_STAGES[i][3];
            k = PROCESS_STAGES[i][1];
            if (time <= PROCESS_STAGES[i][3]){
                num = PROCESS_STAGES[i][0];
                break;
            }
            else {
                time -= PROCESS_STAGES[i][3];
                if (i == OVERTIME_STAGE - 1){
                    num = OVERTIME_STAGE;
                    break;
                }
            }
        }
        this.processStage = num;
        this.processStageTime = time;
    }
    // Возвращает изменение содержания Alc в процессе карбонизации
    double getCarbonationAlc(){
        double cAlc;
        cAlc = CARBONATION_ALC_MAX;
        calcProcessStageAndTime();
        if (getProcessStage()== CARBONATION_STAGE){
            cAlc *= ((double)
                (getProcessProgressFirst() + getProcessProgressSecond())/ 2 / PROGRESS_MAX );
        }
        double i = cAlc;
        return cAlc;
    }
    double alcPercent;      // Содержание алкоголя, %



    int brewStock;          // Остаток к применению , опционально
    int flagNotNullBrew = 0; // Флаг != 0 => варку можно добавлять в базу

    // ******** Конструкторы ******************
    // Инициализация варки с нуля, первичная инициализация параметров, если точнее
    BrewProcess() {
        // **** В части экстракти
        this.nameMaltExt = EMPTY_STRING;    //setNameMaltExt(EMPTY_STRING);
        this.brandMaltExt = EMPTY_STRING;   //setBrandMaltExt(EMPTY_STRING);
        this.weightMaltExt = 0;             //setWeightMaltExt(0);
        this.weightMaltExtCan = 1.7;        //setWeightMaltExtCan(1.7);
        // **** В части праймера
        this.weightDextrosa = 0;         //setWeightDextrosa(1000);
        this.weightSugar = 0;               //setWeightSugar(0);
        this.weightEnhancer = 0;            //setWeightEnhancer(0);
        this.weightThickener = 0;           //setWeightThickener(150);
        // **** Начало варки
        this.beginingDateTimeHrs = 0;       //setBeginingDateTimeHrs(0);
        this.beginningDate = EMPTY_STRING;  //setBeginningDate(EMPTY_STRING);
        this.beginningWortTempereture = NUL_TEMPERATURE; //setBeginningWortTempereture(NUL_...);
        this.ambientTemperature = NUL_TEMPERATURE;  //setAmbientTemperature(NUL_TEMPERATURE);
        this.originalWortGravity = 0;       //setOriginalWortGravity(0);
        // **** Ферментация и итоги
        this.durationOfFermentationHrs = 0; //setDurationOfFermentationHrs(0);
        this.bottlingWortTemperature = 0;   //setBottlingWortTemperature(0);
        this.finalWortGravity = 0;          //setFinalWortGravity(0);
        this.volumeRecomend = 23;           //setVolumeRecomend(23);
        this.volumeActual = 0;              //setVolumeActual(0);
        this.alcoholContent = 0;            //setAlcoholContent(0);
        this.bitterMaltExt = 825;           //setBitterMaltExt(825);
        this.colorMaltExt = 55;             //setColorMaltExt(0);

        this.processStage = 0;
        this.processStageTime = 0;
       // this.context = MainBeerActivity.getCntMBA();
       // this.resources = getResources();  // todo Тут
       // setNameMaltExt(null);
    }
    // нициализация варки из описаний экстрактов (опциональный вариант)
    BrewProcess (int cortegeVerId) {
        this();     // Инит с нуля

        readBrewById(cortegeVerId); // Читаем кортеж варки из базы
            // todo Взять кортеж из базы
        }

    BrewProcess (int cortegeId, int callSource){
        this();
        setNameMaltExt("Real Ale");
        setBrandMaltExt("Finlandia");


    }

    // Методы и ограничения
    void initNewBrew() {} // Подготовка новой варки
    void readBrewById(int BrewId) {


    }    // чтение выбранного кортежа из базы
    void writeBrewById(int BrewId) {}   // запись в базу состояния экземпляра варки



}
