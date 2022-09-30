import java.util.Random;

public class Genome {

    public static final Character[] CHARACTERS = new Character[] { 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '-',
            '\'' };

    private double myMutationRate;
    private int myFitness;
    private StringBuilder myString = new StringBuilder("A");
    private String myTargetName;
    private boolean myCalculateFitnessflag;//record if calculate the fitness or not

    /**
     *
     * @param theMutationRate
     */
    public Genome(double theMutationRate) {
        myMutationRate = theMutationRate;
    }

    /**
     * A copy constructor that initializes a Genome with the same
     * values as the input gene.
     * @param theGene
     */
    public Genome(Genome theGene) {
        myMutationRate = theGene.getMutationRate();
        myString = new StringBuilder(theGene.getString());
        myFitness = theGene.getFitness();
        myTargetName = theGene.getTargetName();
    }

    /**
     * Mutates the string in this genome.
     */
    public void mutate() {
        Random random = new Random();
        double currentRate = random.nextDouble();
        // with mutationRate chance add a character
        if (currentRate <= myMutationRate) {
            addCharRandomly();
        }
        // with mutationRate chance delete a character
        if ((currentRate = random.nextDouble()) <= myMutationRate) {
            deleteCharRandomly();
        }
        // for each character in the string
        for (int i = 0, len = myString.length(); i < len; i++) {
            // with mutationRate chance the character is replaced
            if ((currentRate = random.nextDouble()) <= myMutationRate) {
                replaceCharRandomely(i);
            }
        }
    }

    /**
     * addCharRandomly() - Helper method to complete mutate() function.
     * Add a randomly selected character
     * to a randomly selected position in the string.
     *
     */
    private void addCharRandomly() {
        Random random = new Random();
        int charIndex = random.nextInt(29);
        int addPosition = random.nextInt(myString.length() + 1);

        if (addPosition < myString.length()) {
            myString.insert(addPosition, Genome.CHARACTERS[charIndex]);
        } else {
            myString.append(Genome.CHARACTERS[charIndex]);
        }
    }

    /**
     * DeleteCharRandmly() - Helper method to complete mutate() function.
     * Delete a single character from a randomly selected.
     */
    private void deleteCharRandomly() {
        if (myString.length() == 1)
            return;
        Random deleteRandom = new Random();
        int delPos = deleteRandom.nextInt(myString.length());
        myString.deleteCharAt(delPos);
    }

    /**
     * replaceCharRandomely() - Helper method to complete mutate() function.
     * Replace the character in this position by a randomly selected character.
     *
     * @param theReplacePos certain position in the string that will be replaced.
     */
    private void replaceCharRandomely(int theReplacePos) {
        if (theReplacePos >= myString.length())
            return;
        Random replaceRandom = new Random();
        int charIndex = replaceRandom.nextInt(29);
        myString.setCharAt(theReplacePos, Genome.CHARACTERS[charIndex]);
    }

    /**
     * Update the current Genome by crossing it over with other.
     *
     * @param theOther Genome which we compare to.
     */
    public void crossover(Genome theOther) {
        Random random = new Random();
        // record the length of other's string
        int otherStringLength = theOther.getString().length();
        for (int i = 0, len = myString.length(); i < len; i++) {
            // randomly select a parent string
            int selectedString = random.nextInt(2);

            if (selectedString == 0) {
                // select the local string
                continue;
            } else {
                // select the other string
                if (i < otherStringLength) {
                    // replace the character by other's
                    myString.setCharAt(i, theOther.getString().charAt(i));
                } else {
                    // clear the rest of characters
                    myString.delete(i, myString.length());
                    break;
                }
            }
        }
    }

    /**
     * Get the fitness of the genome.
     *  Let n be the length of the current string.
     *  Let m be the length of the target string.
     * @return
     */
    public Integer fitness() {
        int n = myString.length(), m = myTargetName.length();
        int l = n < m ? n : m;
        int fitnessTemp = Math.abs(n - m) * 2;
        for (int i = 0; i < l; i++) {
            if (myString.charAt(i) != myTargetName.charAt(i))
                fitnessTemp = fitnessTemp + 1;
        }
        myFitness=fitnessTemp;
        myCalculateFitnessflag=true;
        return fitnessTemp;
    }

    /**
     * Wagner-Fischer algorithm for calculating Levenshtein edit
     * distance
     *
     * @return fitness
     */
    public Integer fitness2() {
        int n = myString.length(), m = myTargetName.length();

        int D[][] = new int[n + 1][m + 1];

        for (int i = 0, len = n + 1; i < len; i++)
            D[i][0] = i;

        for (int i = 0, len = m + 1; i < len; i++)
            D[0][i] = i;

        for (int i = 1, leni = n + 1; i < leni; i++)
            for (int j = 1, lenj = m + 1; j < lenj; j++){
                if (myString.charAt(i - 1) == myTargetName.charAt(j - 1))
                    D[i][j] = D[i - 1][j - 1];
                else {
                    int d1 = D[i - 1][j] + 1;
                    int d2 = D[i][j - 1] + 1;
                    int d3 = D[i - 1][j - 1] + 1;
                    D[i][j] = d1 < d2 ? (d1 < d3 ? d1 : d3) : (d2 < d3 ? d2
                            : d3);
                }
            }
        int fitnessTemp = D[n][m] + (Math.abs(n - m) + 1) / 2;
        myFitness=fitnessTemp;
        myCalculateFitnessflag = true;
        return fitnessTemp;
    }

    /**
     * return the Genome's character string and fitness
     */
    public String toString() {
        return  "(\"" + myString + "\", " + getFitness() + ")";
    }

    /*
     * getters and setters
     */
    public double getMutationRate() {
        return myMutationRate;
    }

    public void setMutationRate(double theMutationRate) {
        this.myMutationRate = theMutationRate;
    }

    public StringBuilder getString() {
        return myString;
    }

    public int getFitness() {
        if(!myCalculateFitnessflag) fitness();
        return this.myFitness;
    }

    public String getTargetName() {
        return myTargetName;
    }

    public void setTargetName(String theTargetName) {
        this.myTargetName = theTargetName;
    }

}
