package com.example.workshopjavafxjdbc;

import com.example.workshopjavafxjdbc.db.DbException;
import com.example.workshopjavafxjdbc.services.DepartmentService;
import com.example.workshopjavafxjdbc.services.SellerService;
import com.example.workshopjavafxjdbc.utils.Alerts;
import com.example.workshopjavafxjdbc.utils.Constraints;
import com.example.workshopjavafxjdbc.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {

    private Seller entity;

    private SellerService service;

    private DepartmentService departmentService;

    private List<DataChangeListener> dataChangeListenerList = new ArrayList<>();

    public void setServices(SellerService service, DepartmentService departmentService){
        this.service = service;
        this.departmentService = departmentService;
    }

    @FXML
    private ComboBox<Department> comboBoxDepartment;

    private ObservableList<Department> observableList;

    public void loadAssociateObjects(){
        if(departmentService == null){
            throw new IllegalStateException("Department service was null");
        }
        List<Department> list = departmentService.findAll();
        observableList = FXCollections.observableArrayList(list);
        comboBoxDepartment.setItems(observableList);
    }

    private void initializeComboBoxDepartment() {
        Callback<ListView<Department>, ListCell<Department>> factory = lv -> new ListCell<Department>() {
            @Override
            protected void updateItem(Department item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        comboBoxDepartment.setCellFactory(factory);
        comboBoxDepartment.setButtonCell(factory.call(null));
    }

    public void setSeller(Seller entity){
        this.entity = entity;
    }

    public void subscribeDataChangeListener(DataChangeListener listener){
        dataChangeListenerList.add(listener);
    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        if (entity.getBirthDate() != null){
            dpBirthDate.setValue(LocalDate.ofInstant(entity.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }

        txtBaseSalary.setText(String.format("%.2f",entity.getBaseSalary()));

        if(entity.getDepartment() == null){
            comboBoxDepartment.getSelectionModel().selectFirst();
        } else {
            comboBoxDepartment.setValue(entity.getDepartment());
        }
    }
    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private DatePicker dpBirthDate;

    @FXML
    private TextField txtBaseSalary;

    @FXML
    private Label labelErrorName;

    @FXML
    private Label labelErrorEmail;

    @FXML
    private Label labelErrorBithDate;

    @FXML
    private Label labelErrorBaseSalary;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    @FXML
    public void onBtSaveAction(ActionEvent event){
        if(entity == null){
            throw new IllegalStateException("Entity was null");
        }
        if(service == null){
            throw new IllegalStateException("Service was null");
        }
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (ValidationException e){
            setErrorMessages(e.getErrors());
        } catch (DbException e){
            Alerts.showAlert("Error saving obj", null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener: dataChangeListenerList) {
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Validation error");

        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if (txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "field can't be empty");
        }
        obj.setName(txtName.getText());

        if (txtEmail.getText() == null || txtEmail.getText().trim().equals("")){
            exception.addError("email", "field can't be empty");
        }
        obj.setEmail(txtEmail.getText());

        if(dpBirthDate.getValue() == null){
            exception.addError("birthDate", "field can't be empty");
        } else {
            Instant instant = Instant.from(dpBirthDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setBirthDate(Date.from(instant));
        }

        if (txtBaseSalary.getText() == null || txtBaseSalary.getText().trim().equals("")){
            exception.addError("baseSalary", "field can't be empty");
        }
        obj.setBaseSalary(Utils.tryParseToDouble(txtBaseSalary.getText()));

        obj.setDepartment(comboBoxDepartment.getValue());

        if(exception.getErrors().size() > 0){
            throw exception;
        }

        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName,70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail,60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");

        initializeComboBoxDepartment();
    }

    private void setErrorMessages(Map<String, String> errors){
        Set<String> fields = errors.keySet();

        labelErrorName.setText(fields.contains("name") ? errors.get("name") : "");
        labelErrorEmail.setText(fields.contains("email") ? errors.get("email") : "");
        labelErrorBithDate.setText(fields.contains("birthDate") ? errors.get("birthDate") : "");
        labelErrorBaseSalary.setText(fields.contains("baseSalary") ? errors.get("baseSalary") : "");

    }
}
