//By Michael Doan, Vladimir Hanevich, and Salahuddin Majed
//TCSS 342
//Assignment 2
//Spring 22

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class Main {

    public static void main(String[] args) throws IOException {
        File trace = new File("src/trace.txt");
        PrintStream printStream = new PrintStream(trace);
        String target = "PAULO SERGIO LICCIARDI MESSEDER BARRETO";
        Population population = new Population(100, 0.05);
        population.setTargetName(target);
        int gen = 0;
        int i=0;
        long startTime = System.currentTimeMillis();
        do{
            population.day();
            printStream.write((("Gen " + gen + ": " + population.getMostFit())+"\n").getBytes());
            gen++;
            i++;

        }while(population.getMostFit().getFitness()!=0);
        printStream.write(("Generations: "+i+"\n").getBytes());
        printStream.write(("Running Time: "+(System.currentTimeMillis()-startTime)+" milliseconds").getBytes());
        printStream.close();

        //To run tests on Genome and Population classes
        System.out.println("Genome Test:");
        testGenome();
        System.out.println();
        System.out.println("Population Test:");
        testPopulation();

    }
    //For Testing Genome class
    public static void testGenome() {
        Genome genome = new Genome(0.5);
        genome.setTargetName("PAULO SERGIO LICCIARDI MESSEDER BARRETO");
        Genome genomeDupe = new Genome(genome);
        System.out.println(genome);
        genome.mutate();
        System.out.println(genome);
        genome.mutate();
        System.out.println(genome);
        genome.crossover(genomeDupe);
        System.out.println(genome);
        System.out.println(genome.fitness());
        System.out.println(genome.fitness2());

    }
    //For Testing Population class
    private static void testPopulation() {
        Population population = new Population(100, 0.05);
        population.setTargetName("PAULO SERGIO LICCIARDI MESSEDER BARRETO");
        System.out.println(population.getMostFit());
        population.day();
        System.out.println(population.getMostFit());
    }
}
