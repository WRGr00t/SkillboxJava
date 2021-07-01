import javax.swing.*;
import java.awt.event.ActionListener;

public class FullForm extends JPanel {
    private JPanel rootPanel;
    private JTextField surnameTextField;
    private JTextField nameTextField;
    private JTextField patronymicTextField;
    private JButton applyButton;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;
    }

    public Person getPerson() {
        return new Person(
                surnameTextField.getText().trim(),
                nameTextField.getText().trim(),
                patronymicTextField.getText().trim());
    }

    public void setPerson(Person person) {
        surnameTextField.setText(person.getSurname());
        nameTextField.setText(person.getName());
        patronymicTextField.setText(person.getPatronymic());
    }

    public void addApplyListener(ActionListener listener) {
        applyButton.addActionListener(listener);
    }
}
