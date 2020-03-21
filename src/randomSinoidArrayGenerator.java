import java.util.Random;

public class randomSinoidArrayGenerator {

    public static int[] getWaves(int width, int startHeight, int endHeight, int maxHeight, int minHeight, int minStepHeight, int maxStepHeight, int minStepWidth, int maxStepWidth){


        Random r = new Random();
        int[] output = new int[width];

        int currentHeight = startHeight;
        int currentPoint = 1;


        while(currentPoint < width){


            // set the height change of this step
            int thisStepEndHeigth = currentHeight;
            int loopCounter = 0;
            while ( thisStepEndHeigth > maxHeight || thisStepEndHeigth < minHeight || thisStepEndHeigth == currentHeight){

                //bias that influences which direction the heightStep generation should go 0.5 should be when on point     lower bias means goes up        higher bias means go down
                double endHeightBias;
                int heightdifference = Math.abs(endHeight - currentHeight);
                if (heightdifference > maxStepHeight){
                    if (currentHeight < endHeight) {
                        // bias should be low
                        endHeightBias = 0.1;
                    } else {
                        //bias should be high
                        endHeightBias = 0.7;
                    }
                }else{
                    endHeightBias = 0.5;
                }

                thisStepEndHeigth = (int)(currentHeight + ((r.nextDouble()- endHeightBias )*((double)maxStepHeight - minStepHeight)*2));
                //<editor-fold desc="loop failsafe">
                loopCounter++;
                if (loopCounter > 1000){
                    System.out.println("went infinite randomizing height");
                    //System.exit(0);
                }
                //</editor-fold>
            }
            int change = thisStepEndHeigth - currentHeight;


            //set the width of the step
            int currentStepWidth = 0;
            loopCounter = 0;
            while ( currentStepWidth < minStepWidth || currentStepWidth > maxStepWidth) {
                currentStepWidth = (int)(minStepWidth + (r.nextDouble() * (double)(maxStepWidth - minStepWidth)));
                //<editor-fold desc="loop failsafe">
                loopCounter++;
                if (loopCounter > 1000){
                    System.out.println("went infinite randomizing width");
                    //System.exit(0);
                }
                //</editor-fold>
            }

            if(currentPoint + currentStepWidth > width ){
                currentStepWidth = width - currentPoint;
                change = endHeight - currentHeight;//testing
                System.out.println("made a forced height change of " + change);
            }

            //fill width till nextStop
            for (int i = 0; i < currentStepWidth; i++) {
                output[currentPoint+i-1] = waveFunc(i, currentHeight, change, currentStepWidth);
            }

            currentHeight = thisStepEndHeigth;
            currentPoint += currentStepWidth;
        }

        return output;

    }

    private static int waveFunc(int step, int start, int change, int duration){
        return (int)(-change/2 * (Math.cos(Math.PI*step/duration) - 1) + start);
    }

    public static void printIntArray(int[] inp, int width, int height){

        for (int y = height; y >= 0 ; y--) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {
                if (inp[x] > y){
                    sb.append("*");
                }else{
                    sb.append(" ");
                }
            }
            System.out.println(sb.toString());
        }
    }



}
