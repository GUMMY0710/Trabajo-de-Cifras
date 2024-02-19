import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CifrasSignificativas {

    public static void main(String[] args) {
        // Crear y mostrar la interfaz gráfica en el hilo de despacho de eventos (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            // Crear el panel de entrada
            JPanel inputPanel = new JPanel();
            JLabel label = new JLabel("Ingresa un número: ");
            JTextField textField = new JTextField(10);
            JButton calcularButton = new JButton("Calcular");

            // Agregar ActionListener al botón de calcular
            calcularButton.addActionListener(e -> {
                try {
                    double numero = Double.parseDouble(textField.getText());
                    int cifrasSignificativas = getCifrasSignificativas(numero);

                    // Mostrar el resultado en una ventana emergente
                    JOptionPane.showMessageDialog(null,
                            "El número " + numero + " tiene " + cifrasSignificativas + " cifras significativas.",
                            "Resultado",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    // Mostrar mensaje de error en una ventana emergente
                    JOptionPane.showMessageDialog(null,
                            "Error: Ingresa un número válido.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            });

            // Agregar componentes al panel de entrada
            inputPanel.add(label);
            inputPanel.add(textField);
            inputPanel.add(calcularButton);

            // Crear el contenedor principal
            JPanel mainPanel = new JPanel();
            mainPanel.add(inputPanel);

            // Crear la ventana principal
            JFrame frame = new JFrame("Cifras Significativas");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
            frame.setVisible(true);
        });
    }

    private static int getCifrasSignificativas(double numero) {
        // Lógica para determinar cifras significativas según las reglas especificadas
        if (numero == 0) {
            return 1; // Cero tiene una cifra significativa
        }

        String numStr = Double.toString(Math.abs(numero));

        // Contar los dígitos antes del punto decimal
        int significantDigits = 0;
        boolean afterDecimal = false;
        boolean beforeFirstNonZero = true;

        for (int i = 0; i < numStr.length(); i++) {
            char currentChar = numStr.charAt(i);

            if (currentChar == '0' && beforeFirstNonZero) {
                // No cuenta los ceros a la izquierda del primer dígito significativo
                // antes del punto decimal
                continue;
            } else if (currentChar == '0') {
                // Cuenta los ceros al final de la parte decimal como significativos
                significantDigits++;
            } else if (currentChar != '.') {
                significantDigits++;
                afterDecimal = (currentChar == '.');
                beforeFirstNonZero = false;
            }
        }

        return significantDigits;
    }
}
