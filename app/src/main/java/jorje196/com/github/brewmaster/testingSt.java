package jorje196.com.github.brewmaster;

/*
 * Created by User on 30.10.2017.
 */
/* Цель - практика применения механизма тестирования блоков
    (Small tests) .
 */
 class testingSt {
    private int x=0;
    private int y=0;
    testingSt(int x, int y) {
        this.x=x;
        this.y=y;
    }
    int addFunc() {
        return x+y;
    }
}
