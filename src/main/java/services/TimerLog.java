package services;

import java.util.Timer;
import java.util.TimerTask;

public class TimerLog{
	public static void main(String[] args) {
		agendarTarefa(6000); // 3600000 milissegundos = 1 hora
	}
	
	public static void agendarTarefa(long intervalo) {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				Log.main(null); // Executa a classe Log
			}
		};
		
		// Agendar a tarefa para ser executada a cada intervalo especificado
		timer.scheduleAtFixedRate(task, 0, intervalo);
	}
}
