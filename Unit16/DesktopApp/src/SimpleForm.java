import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;

public class SimpleForm extends JPanel {
    private JPanel rootPanel;
    private JTextField textField;
    private JButton applyButton;
    private JProgressBar inputProgressBar;
    { //слушатель изменений в поле ввода для реакции в прогресс-баре
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateProgressBar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateProgressBar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        rootPanel = this;
    }
    //перенос данных из поля в объект
    public void setPerson(Person person){
        textField.setText(person.toString());
    }

    //
    public Person getPerson(){
        String[] data = getToken();
        switch (data.length){
            case 0: return new Person();
            case 1: return new Person(data[0],"","");
            case 2: return new Person(data[0], data[1],"");
            case 3: return new Person(data[0], data[1], data[2]);
            default: return null;
        }
    }
    //слушатель кнопки
    public void addApplyListener(ActionListener listener){
        applyButton.addActionListener(listener);
    }

    //разбиение текста из поля на компоненты объекта
    protected String[] getToken(){
        String str = textField.getText().trim();
        if (str.isEmpty()) return new String[0];
        else return str.split("\\s+");
    }
    //обработчик изменений в поле ввода и вынос реакции в прогресс-бар
    protected void updateProgressBar(){
        inputProgressBar.setValue(getToken().length);
    }
}
