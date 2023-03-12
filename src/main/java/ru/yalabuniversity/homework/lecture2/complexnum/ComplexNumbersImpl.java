package ru.yalabuniversity.homework.lecture2.complexnum;

public class ComplexNumbersImpl implements ComplexNumbers {
    private double real;
    private double image;

    public ComplexNumbersImpl(double real){
        this.real = real;
    }

    public ComplexNumbersImpl(double real, double image) {
        this.real = real;
        this.image = image;
    }

    @Override
    public double getImage() {
        return image;
    }

    @Override
    public double getReal() {
        return real;
    }

    @Override
    public ComplexNumbers sum(ComplexNumbers complexNumber) {
        return new ComplexNumbersImpl(this.real + complexNumber.getReal(), this.image + complexNumber.getImage());
    }

    @Override
    public ComplexNumbers divide(ComplexNumbers complexNumber) {
        return new ComplexNumbersImpl(this.real - complexNumber.getReal(), this.image - complexNumber.getImage());
    }

    // (a + b*i) * (c + d*i) = a*c + b*d*(-1) + a*d*i + c*b*i = a*c + b*d*(-1) + (a*d + c*b)*i
    // где a*c + b*d*(-1) -> действительная часть
    // (a*d + c*b)*i      -> мнимая часть
    // по этому же принципу запрограммируем произведение
    @Override
    public ComplexNumbers multiply(ComplexNumbers complexNumber) {
        double newReal = this.real * complexNumber.getReal() + this.image * complexNumber.getImage() * -1;
        double newImage = this.real * complexNumber.getImage() + complexNumber.getReal() * this.getImage();
        return new ComplexNumbersImpl(newReal, newImage);
    }

    @Override
    public double module() {
        return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.image, 2));
    }

    @Override
    public String toString() {
        String imagePart;
        if(image == 0)
            imagePart = "";
        else if(image > 0)
            imagePart = "+" + image + "i";
        else
            imagePart = image + "i";
        return real + imagePart;
    }
}
