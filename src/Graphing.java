import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;


public class Graphing extends ApplicationFrame {
    DefaultCategoryDataset list;
    public Graphing(String title, DefaultCategoryDataset list) {
        super(title);
        this.list = list;
        JFreeChart barChart = ChartFactory.createBarChart("Word Frequency", "Word", "Frequency", list,
                PlotOrientation.VERTICAL, false, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        setContentPane(chartPanel);
    }

    public void graph()  {
        Graphing chart = new Graphing("rand", list);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen( chart );
        chart.setVisible( true );
    }


    public static void main(String[] args) {
        DefaultCategoryDataset list = new DefaultCategoryDataset();
        for (int i = 0; i < 1000; i++) {
            String j = String.valueOf(i);
            list.addValue(i, j, "word");
        }
        Graphing chart = new Graphing("rand", list);
        chart.graph();
    }
}
