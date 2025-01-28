package com.talk;

import java.time.*;
import java.time.format.*;
import java.util.*;
import javax.swing.*;

/*
 * Класс, запускающий таймер в отдельном потоке
 */

public class TimerWorker extends SwingWorker<Void,String>{
    private LearningScreen UI;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");
    private LocalTime startTime = LocalTime.of(0, 1, 0);
    private LocalTime finishTime = LocalTime.of(0, 0, 0);

    public TimerWorker(LearningScreen UI){
        this.UI = UI;
    }

    @Override
    /*
     * Задача, выполняемая в фоне
     * Изменяем значение LocalTime на 1 и обновляем JLabel, а затем ждём секунду
     */
    protected Void doInBackground() throws Exception{
        while(!startTime.equals(finishTime)){
            if(isCancelled()){
                System.out.println("Task finished");
                return null;
            }
            publish(startTime.format(formatter));
            Thread.sleep(1000);
            startTime = startTime.minusSeconds(1);
        }
        return null;
    }

    @Override
    /*
     * Вызывается каждый раз, когда необходимо обновить JLabel
     * Должен принимать список, хотя в данном случае значение всего одно
     */
    protected void process(List<String> data){
        for(String newData: data){
            UI.updateTimer(newData);
        }
    }

    @Override
    /*
     * Вызывается по окончании работы потока 
     * Выполняется в основном потоке GUI
     */
    protected void done(){
        UI.updateTimer(finishTime.format(formatter));
        if(isCancelled()) return;
        int res = UI.blockAndFinish();
        switch(res){
            case 0: UI.dispose(); new LearningScreen();
            case 1: UI.dispose();
        }
    }
}
