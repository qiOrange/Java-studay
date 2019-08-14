package com.github.shiro.config;

class Food {

}
class Fish extends Food{
	
}
class Bone extends Food{
	
}
class Animal{
	public void eat(Food food) {
		
	}
}
class Cat extends Animal{
	
}
class Dog extends Animal{
	
}
class Feeder{
	public void feed(Animal animal,Food food){
		animal.eat(food);
	}
}
public class Demo{
	public static void main(String[] args) {
int a=0;
boolean as = a++>1&a-->1;
System.out.println(as);
	}
	
}