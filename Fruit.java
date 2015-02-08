public class Fruit extends Food {
	
  void eaten(Animal animal) {
    System.out.println("animal eats fruit");
  }
  void eaten(Dog dog) {
    System.out.println("dog eats fruit");
  } 
  void eaten(Cat cat) {
    System.out.println("cat eats fruit");
  } 
}
