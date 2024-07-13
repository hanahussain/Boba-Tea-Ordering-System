public interface Cost{
  double[] teaCost = {4, 4, 4.5, 4.5, 4};
  double[] toppingCost = {0.5, 0.65, 0.65, 0.8, 0.65};

  public abstract double calculatePrice(Drink d);
} //end Cost
