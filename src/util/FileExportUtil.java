package util;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileExportUtil {
    public static void exportTableToCSV(JTable table, String filePath) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            TableModel model = table.getModel();
            // Header
            for (int i = 0; i < model.getColumnCount(); i++) {
                pw.print(model.getColumnName(i));
                if (i < model.getColumnCount() - 1) pw.print(",");
            }
            pw.println();

            // Rows
            for (int row = 0; row < model.getRowCount(); row++) {
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object val = model.getValueAt(row, col);
                    pw.print(val != null ? val.toString().replace(",", ";") : "");
                    if (col < model.getColumnCount() - 1) pw.print(",");
                }
                pw.println();
            }

            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
