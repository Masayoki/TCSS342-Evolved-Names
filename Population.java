import java.util.Random;

class Population {
    private Genome mostFit = null;
    private Genome[] genomes;
    private int numGenomes;

    Population(int numGenomes, double mutationRate) {
        genomes = new Genome[numGenomes];
        this.numGenomes = numGenomes;
        for (int g = 0; g < numGenomes; g++) {
            Genome newGenome = new Genome(mutationRate);
            genomes[g] = newGenome;
        }
    }

    private void mergeArray(Genome[] genomes, int top, int mid, int end,
                            Genome[] genomeTemp) {
        int t = top, j = mid + 1, m = mid, e = end, k = 0;
        while (t <= m && j <= e) {
            if (genomes[t].getFitness() <= genomes[j].getFitness())
                genomeTemp[k++] = genomes[t++];
            else
                genomeTemp[k++] = genomes[j++];
        }
        while (t <= m)
            genomeTemp[k++] = genomes[t++];
        while (j <= e)
            genomeTemp[k++] = genomes[j++];
        for (t = 0; t < k; t++)
            genomes[top + t] = genomeTemp[t];
    }

    private void mergeSort(Genome[] genomes, int top, int end,
                           Genome[] genomeTemp) {
        if (top < end) {
            int mid = (top + end) / 2;
            mergeSort(genomes, top, mid, genomeTemp);
            mergeSort(genomes, mid + 1, end, genomeTemp);
            mergeArray(genomes, top, mid, end, genomeTemp);
        }
    }

    public void day() {
        mergeSort(genomes, 0, genomes.length - 1, new Genome[genomes.length]);
        mostFit = genomes[0];
        int g = numGenomes / 2;
        int j = numGenomes / 2;

        while (g++ < numGenomes - 1) {
            Random random = new Random();
            Genome genoSelect = genomes[random.nextInt(j)];
            int methSelected = random.nextInt(2);
            if (methSelected == 0) {
                Genome genoAdd = new Genome(genoSelect);
                genoAdd.mutate();
                genomes[g] = genoAdd;
            } else {
                Genome genoAdd = new Genome(genoSelect);
                Genome genoSlected2 = genomes[random.nextInt(j)];
                genoAdd.crossover(genoSlected2);
                genoAdd.mutate();
                genomes[g] = genoAdd;
            }
        }
    }

    public void setTargetName(String targetName) {
        for (Genome genome : genomes) {
            genome.setTargetName(targetName);
        }
    }

    public Genome getMostFit() {
        return mostFit;
    }
}
