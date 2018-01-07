package jorje196.com.github.brewmaster;

import android.database.Cursor;

/**
 * Created by User on 05.01.2018.
 * Класс процесса варки, запись новой информации о варках возможна
 * только через него, он же контролирует корректность данных .
 */

public class BrewProcess {
    public String getStartDate() {
        return startDate;
    }

    // Свойства объекта
    String startDate;       // Дата начала процесса

    public String getStartTime() {
        return startTime;
    }

    String startTime;       // Время -//-, опционально

    public String getBottlingDate() {
        return bottlingDate;
    }

    String bottlingDate;    // Дата розлива

    public String getBottlingTime() {
        return bottlingTime;
    }

    String bottlingTime;    // Время -//-, опционально

    int carbonizaitionPeriod;   // Время на карбонизацию (втор. ферментация), дни
    int ripeningPeriod;    // Период дозревания (заверш. втор. фермент), дни (от 2 до 12 недель)
    int ageingPeriod;      // Период выдержки, созревания, дни (чем темнее, тем больше)
    int bestBefore;        // Измеряется в днях

    int startTemp;       // Начальная температура сусла, градусы Цельсия (Фаренгейт опционально)
    int bottlingTemp;    // Температура сусла при розливе, -//-
    int ambientTemp;     // Температура окружающей среды, -//-

    double startGravity;  // Плотность сусла на старте процесса
    double finalGravity;  // Плотность перед розливом

    double voumeRecomend; // Рекомендуемый объем сусла, литры
    double volumeActual;  // Фактический объем сусла, литры

    String nameMaltExt;     // Наименование охмеленного экстракта
    String brandMaltExt;    // Производитель -//-
    double weightSugar;     // Кол-во добавленного в сусло сахара, кг
    double weightDextrosa;  // -//- декстрозы
    String thickenerName;   // Используемый загуститель
    double weightThickener; // Кол-во добавленного загустителя, кг
    String enhancerName;    // Название усилителя
    double weightEnhancer;  // Кол-во добавленного усилителя, кг
    double alcPercent;      // Содержание алкоголя, %

    int processState;       // Завершенность активной фазы (до уст. на созревание)
    int processStage;       // Этап процесса (от 0 до 2-3 лет)
    int brewStock;          // Остаток к применению , опционально

    BrewProcess (int BrewId) {
        if( BrewId == 0 ){
            initNewBrew();  // Новая варка
        } else {
            readBrewById(BrewId); // Читаем кортеж варки из базы
            // todo Взять кортеж из базы
        }
    }

    // Методы и ограничения
    void initNewBrew() {} // Подготовка новой варки
    void readBrewById(int BrewId) {


    }    // чтение выбранного кортежа из базы
    void writeBrewById(int BrewId) {}   // запись в базу состояния экземпляра варки



}
