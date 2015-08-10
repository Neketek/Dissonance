package com.balakin.dissonance.logic.parts.optimization;

public class Formulas{
	private static final float MS_SCALE_CONSTANT = 10*10*10;
	/**
	 * Функция, которая определяет шаг, необходимый для того, что бы точка прошла интервал
	 * от начала до конца за определенное количество секунд, учитывая длинну временного шага.
	 * @param l длинна интервала
	 * @param n количество секунд
	 * @param t временной интервал в милисекундах
	 * @return длинна шага(скорость)
	 */
	public static float v(float l,float n,int t){
		return (l*t)/(MS_SCALE_CONSTANT*n);
	}
	/**
	 * Функция, которая возвращает количество обновлений за
	 * определенный вещественный интервал секунд
	 * при заданном времени обновления в мс.
	 * @param t время одного обновления в мс
	 * @param n длинна временного интервала
	 * @return количество обновлений за интервал
	 */
	public static int n(int t, float n){
		return (int) (n/t*MS_SCALE_CONSTANT);
	}
	public static float fallTimeInSeconds(float length,float speed,int mpu){
		return length/speed*(mpu/MS_SCALE_CONSTANT);
	}
}
