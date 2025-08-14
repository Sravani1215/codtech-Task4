import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Starting Mahout Recommender...");

            // Load CSV from resources folder
            File dataFile = new File(Main.class.getClassLoader().getResource("data.csv").getFile());
            DataModel model = new FileDataModel(dataFile);

            // Calculate similarity between users
            UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

            // Find nearest 2 neighbors
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

            // Create recommender
            GenericUserBasedRecommender recommender =
                    new GenericUserBasedRecommender(model, neighborhood, similarity);

            // Recommend 3 items for user 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 3);

            // Print recommendations
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("Recommended item: " +
                        recommendation.getItemID() +
                        " (value: " + recommendation.getValue() + ")");
            }

            System.out.println("Done!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
