package com.lenguajecafetero.vista;

import com.lenguajecafetero.controlador.AcademiaController;
import com.lenguajecafetero.modelo.AsignacionProfesor;
import com.lenguajecafetero.modelo.Curso;
import com.lenguajecafetero.modelo.CursoPersonalizado;
import com.lenguajecafetero.modelo.Estudiante;
import com.lenguajecafetero.modelo.Matricula;
import com.lenguajecafetero.modelo.Profesor;
import com.lenguajecafetero.modelo.ServicioAdicional;
import com.lenguajecafetero.modelo.enums.NivelIdioma;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.function.Function;

public class AcademiaView extends BorderPane {

    private final AcademiaController controller;
    private final StackPane pageHost = new StackPane();
    private final Label pageTitle = new Label();
    private final Label pageSubtitle = new Label();
    private final Label status = new Label("Datos de demostracion cargados");
    private final NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    // Cada pagina se construye una sola vez y se reutiliza al volver a ella,
    // asi las tablas y los formularios no se pierden al cambiar de seccion.
    private final Map<String, javafx.scene.control.ScrollPane> paginas = new HashMap<>();

    private Label studentCount;
    private Label courseCount;
    private Label enrollmentCount;
    private Label incomeTotal;
    private TableView<Matricula> dashboardEnrollments;

    private final TextField studentName = field("Nombre completo");
    private final TextField studentDocument = field("Documento");
    private final TextField studentPhone = field("Telefono");
    private final TextField studentEmail = field("Correo");
    private final TextField studentAge = field("Edad");
    private TableView<Estudiante> studentsTable;

    private final ComboBox<String> courseType = new ComboBox<>();
    private final TextField courseCode = field("Codigo");
    private final TextField courseName = field("Nombre del curso");
    private final TextField courseLanguage = field("Idioma");
    private final TextField courseDescription = field("Descripcion");
    private final TextField courseDuration = field("Duracion (meses)");
    private final TextField courseMonthlyValue = field("Valor mensual");
    private final TextField courseSessions = field("Sesiones personalizadas");
    private final ComboBox<NivelIdioma> courseLevel = new ComboBox<>();
    private final TextField courseObjectives = field("Objetivos");
    private final TextField courseTeacherRate = field("Tarifa por sesion");
    private final VBox customCourseFields = new VBox(10);
    private TableView<Curso> coursesTable;

    private final ComboBox<Estudiante> enrollmentStudent = new ComboBox<>();
    private final ComboBox<Curso> enrollmentCourse = new ComboBox<>();
    private final ComboBox<Object> enrollmentService = new ComboBox<>();
    private final TextField enrollmentDiscount = field("Descuento (%)");
    private TableView<Matricula> enrollmentsTable;

    private final TextField teacherId = field("Identificacion");
    private final TextField teacherName = field("Nombre del profesor");
    private final TextField teacherLanguage = field("Idioma");
    private final TextField teacherPhone = field("Telefono");
    private final TextField teacherRate = field("Tarifa por sesion");
    private final ComboBox<Estudiante> assignmentStudent = new ComboBox<>();
    private final ComboBox<CursoPersonalizado> assignmentCourse = new ComboBox<>();
    private final ComboBox<Profesor> assignmentTeacher = new ComboBox<>();
    private TableView<Profesor> teachersTable;
    private TableView<AsignacionProfesor> assignmentsTable;

    public AcademiaView(AcademiaController controller) {
        this.controller = controller;
        getStyleClass().add("app-shell");
        setLeft(buildSidebar());
        setTop(buildHeader());
        setCenter(pageHost);
        setBottom(status);
        status.getStyleClass().add("status-bar");
        configureCombos();
        courseType.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean personalized = "Personalizado".equals(newValue);
            customCourseFields.setManaged(personalized);
            customCourseFields.setVisible(personalized);
        });
        showPage("Resumen");
    }

    private Node buildSidebar() {
        VBox sidebar = new VBox(12);
        sidebar.setPrefWidth(218);
        sidebar.getStyleClass().add("sidebar");
        HBox brand = new HBox(11);
        brand.setAlignment(Pos.CENTER_LEFT);
        Label mark = new Label("LC");
        mark.getStyleClass().add("brand-mark");
        VBox identity = new VBox(2, styledLabel("Lenguaje Cafetero", "brand-title"),
                styledLabel("GESTION ACADEMICA", "brand-caption"));
        brand.getChildren().addAll(mark, identity);
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        sidebar.getChildren().addAll(brand, new Region());
        for (String section : List.of("Resumen", "Estudiantes", "Cursos", "Matriculas", "Equipo y asignaciones")) {
            Button button = new Button(section);
            button.setMaxWidth(Double.MAX_VALUE);
            button.getStyleClass().add("nav-button");
            button.setOnAction(event -> showPage(section));
            sidebar.getChildren().add(button);
        }
        sidebar.getChildren().addAll(spacer, styledLabel("ACADEMIA DE IDIOMAS", "sidebar-note"));
        return sidebar;
    }

    private Node buildHeader() {
        VBox header = new VBox(4, pageTitle, pageSubtitle);
        header.getStyleClass().add("topbar");
        pageTitle.getStyleClass().add("page-title");
        pageSubtitle.getStyleClass().add("page-subtitle");
        return header;
    }

    private void showPage(String section) {
        String subtitle;
        switch (section) {
            case "Estudiantes" -> subtitle = "Directorio y registro de estudiantes";
            case "Cursos" -> subtitle = "Catalogo, modalidades y valores";
            case "Matriculas" -> subtitle = "Inscripciones, servicios y cobros";
            case "Equipo y asignaciones" -> subtitle = "Profesores y acompanamiento personalizado";
            default -> {
                section = "Resumen";
                subtitle = "Vista general de la operacion academica";
            }
        }
        String selectedSection = section;
        pageTitle.setText(section);
        pageSubtitle.setText(subtitle);

        javafx.scene.control.ScrollPane scroll = paginas.computeIfAbsent(section, this::crearPagina);
        pageHost.getChildren().setAll(scroll);
        refreshAll(); // muestra los datos actuales del modelo en la pagina elegida

        getLeft().lookupAll(".nav-button").forEach(node -> {
            Button button = (Button) node;
            if (button.getText().equals(selectedSection)) {
                if (!button.getStyleClass().contains("selected")) button.getStyleClass().add("selected");
            } else {
                button.getStyleClass().remove("selected");
            }
        });
    }

    private javafx.scene.control.ScrollPane crearPagina(String section) {
        Node content = switch (section) {
            case "Estudiantes" -> studentsPage();
            case "Cursos" -> coursesPage();
            case "Matriculas" -> enrollmentsPage();
            case "Equipo y asignaciones" -> relationshipsPage();
            default -> dashboardPage();
        };
        javafx.scene.control.ScrollPane scroll = new javafx.scene.control.ScrollPane(content);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        return scroll;
    }

    private Node dashboardPage() {
        VBox page = pageContent();
        HBox metrics = new HBox(14);
        studentCount = metric(metrics, "ESTUDIANTES", "0", "Registros activos");
        courseCount = metric(metrics, "CURSOS", "0", "En el catalogo");
        enrollmentCount = metric(metrics, "MATRICULAS", "0", "Inscripciones registradas");
        incomeTotal = metric(metrics, "INGRESOS DEL MES", "$0", "Acumulado del periodo");
        for (Node node : metrics.getChildren()) HBox.setHgrow(node, Priority.ALWAYS);
        Label recentTitle = styledLabel("Actividad reciente", "section-title");
        dashboardEnrollments = new TableView<>();
        dashboardEnrollments.setPlaceholder(new Label("Aun no hay matriculas"));
        dashboardEnrollments.getColumns().setAll(
                column("Codigo", enrollment -> enrollment.getId()),
                column("Estudiante", enrollment -> enrollment.getEstudiante().getNombreCompleto()),
                column("Curso", enrollment -> enrollment.getCurso().getNombre()),
                column("Fecha", enrollment -> enrollment.getFechaMatricula().toString()),
                column("Valor", enrollment -> money(enrollment.getValorFinal())));
        dashboardEnrollments.setPrefHeight(280);
        HBox actions = new HBox(10,
                actionButton("Registrar estudiante", "primary-button", () -> showPage("Estudiantes")),
                actionButton("Crear matricula", "secondary-button", () -> showPage("Matriculas")));
        page.getChildren().addAll(metrics, recentTitle, dashboardEnrollments, actions);
        return page;
    }

    private Node studentsPage() {
        studentsTable = new TableView<>();
        studentsTable.setPlaceholder(new Label("Registra el primer estudiante"));
        studentsTable.getColumns().setAll(
                column("Nombre", Estudiante::getNombreCompleto),
                column("Documento", Estudiante::getDocumentoIdentidad),
                column("Correo", Estudiante::getCorreo),
                column("Telefono", Estudiante::getTelefono));
        GridPane form = formGrid();
        addField(form, 0, "Nombre", studentName);
        addField(form, 1, "Documento", studentDocument);
        addField(form, 2, "Telefono", studentPhone);
        addField(form, 3, "Correo", studentEmail);
        addField(form, 4, "Edad", studentAge);
        Button save = actionButton("Guardar estudiante", "primary-button", this::saveStudent);
        VBox panel = formPanel("Nuevo estudiante", form, save);
        HBox layout = new HBox(18, panel, studentsTable);
        HBox.setHgrow(studentsTable, Priority.ALWAYS);
        HBox.setHgrow(panel, Priority.NEVER);
        return pageContent(layout);
    }

    private Node coursesPage() {
        coursesTable = new TableView<>();
        coursesTable.setPlaceholder(new Label("El catalogo esta vacio"));
        coursesTable.getColumns().setAll(
                column("Codigo", Curso::getCodigo),
                column("Curso", Curso::getNombre),
                column("Idioma", Curso::getIdioma),
                column("Modalidad", course -> course.getClass().getSimpleName().replace("Curso", "")),
                column("Valor base", course -> money(course.calcularValorBase())));
        courseType.getItems().setAll("Regular", "Intensivo", "Personalizado");
        courseType.getSelectionModel().selectFirst();
        courseLevel.getItems().setAll(NivelIdioma.values());
        courseLevel.getSelectionModel().select(NivelIdioma.B1);
        customCourseFields.getChildren().setAll(
                labeled("Sesiones", courseSessions), labeled("Nivel objetivo", courseLevel),
                labeled("Objetivos", courseObjectives), labeled("Tarifa por sesion", courseTeacherRate));
        customCourseFields.setManaged(false);
        customCourseFields.setVisible(false);
        GridPane form = formGrid();
        addField(form, 0, "Modalidad", courseType);
        addField(form, 1, "Codigo", courseCode);
        addField(form, 2, "Nombre", courseName);
        addField(form, 3, "Idioma", courseLanguage);
        addField(form, 4, "Descripcion", courseDescription);
        addField(form, 5, "Duracion (meses)", courseDuration);
        addField(form, 6, "Valor mensual", courseMonthlyValue);
        Button save = actionButton("Crear curso", "primary-button", this::saveCourse);
        VBox panel = formPanel("Alta de curso", form, customCourseFields, save);
        HBox layout = new HBox(18, panel, coursesTable);
        HBox.setHgrow(coursesTable, Priority.ALWAYS);
        return pageContent(layout);
    }

    private Node enrollmentsPage() {
        enrollmentsTable = new TableView<>();
        enrollmentsTable.setPlaceholder(new Label("No hay matriculas registradas"));
        enrollmentsTable.getColumns().setAll(
                column("Codigo", Matricula::getId),
                column("Estudiante", enrollment -> enrollment.getEstudiante().getNombreCompleto()),
                column("Curso", enrollment -> enrollment.getCurso().getNombre()),
                column("Fecha", enrollment -> enrollment.getFechaMatricula().toString()),
                column("Descuento", enrollment -> String.format("%.0f%%", enrollment.getDescuento() * 100)),
                column("Total", enrollment -> money(enrollment.getValorFinal())));
        enrollmentStudent.setPromptText("Selecciona estudiante");
        enrollmentCourse.setPromptText("Selecciona curso");
        enrollmentService.setPromptText("Servicio opcional");
        enrollmentDiscount.setPromptText("0");
        GridPane form = formGrid();
        addField(form, 0, "Estudiante", enrollmentStudent);
        addField(form, 1, "Curso", enrollmentCourse);
        addField(form, 2, "Servicio", enrollmentService);
        addField(form, 3, "Descuento (%)", enrollmentDiscount);
        Label note = styledLabel("El total se calcula con el valor del curso y los servicios seleccionados.", "muted");
        note.setWrapText(true);
        Button create = actionButton("Confirmar matricula", "primary-button", this::saveEnrollment);
        VBox panel = formPanel("Nueva matricula", form, note, create);
        VBox layout = new VBox(18, panel, enrollmentsTable);
        return pageContent(layout);
    }

    private Node relationshipsPage() {
        teachersTable = new TableView<>();
        teachersTable.setPlaceholder(new Label("No hay profesores registrados"));
        teachersTable.getColumns().setAll(
                column("ID", Profesor::getIdentificacion), column("Profesor", Profesor::getNombre),
                column("Idioma", Profesor::getIdioma), column("Tarifa", teacher -> money(teacher.getTarifaPorSesion())));
        assignmentsTable = new TableView<>();
        assignmentsTable.setPlaceholder(new Label("No hay asignaciones registradas"));
        assignmentsTable.getColumns().setAll(
                column("Estudiante", assignment -> assignment.getEstudiante().getNombreCompleto()),
                column("Curso", assignment -> assignment.getCurso().getNombre()),
                column("Profesor", assignment -> assignment.getProfesor().getNombre()),
                column("Fecha", assignment -> assignment.getFechaAsignacion().toString()));
        assignmentStudent.setPromptText("Estudiante");
        assignmentCourse.setPromptText("Curso personalizado");
        assignmentTeacher.setPromptText("Profesor");
        GridPane teacherForm = formGrid();
        addField(teacherForm, 0, "Identificacion", teacherId);
        addField(teacherForm, 1, "Nombre", teacherName);
        addField(teacherForm, 2, "Idioma", teacherLanguage);
        addField(teacherForm, 3, "Telefono", teacherPhone);
        addField(teacherForm, 4, "Tarifa por sesion", teacherRate);
        VBox teacherPanel = formPanel("Registrar profesor", teacherForm,
                actionButton("Guardar profesor", "primary-button", this::saveTeacher));
        GridPane assignmentForm = formGrid();
        addField(assignmentForm, 0, "Estudiante", assignmentStudent);
        addField(assignmentForm, 1, "Curso personalizado", assignmentCourse);
        addField(assignmentForm, 2, "Profesor", assignmentTeacher);
        VBox assignmentPanel = formPanel("Asignar acompanamiento", assignmentForm,
                actionButton("Crear asignacion", "primary-button", this::saveAssignment));
        HBox forms = new HBox(18, teacherPanel, assignmentPanel);
        HBox.setHgrow(teacherPanel, Priority.ALWAYS);
        HBox.setHgrow(assignmentPanel, Priority.ALWAYS);
        HBox tables = new HBox(18, teachersTable, assignmentsTable);
        HBox.setHgrow(teachersTable, Priority.ALWAYS);
        HBox.setHgrow(assignmentsTable, Priority.ALWAYS);
        teachersTable.setPrefHeight(230);
        assignmentsTable.setPrefHeight(230);
        return pageContent(forms, styledLabel("Equipo", "section-title"), tables);
    }


    private void saveStudent() {
        if (runAction(() -> controller.registrarEstudiante(studentName.getText(), studentDocument.getText(),
                        studentPhone.getText(), studentEmail.getText(), Integer.parseInt(studentAge.getText().trim())),
                "Estudiante registrado.")) {
            studentName.clear(); studentDocument.clear(); studentPhone.clear(); studentEmail.clear(); studentAge.clear();
        }
    }

    private void saveCourse() {
        if (runAction(() -> controller.crearCurso(courseType.getValue(), courseCode.getText(), courseName.getText(),
                        courseLanguage.getText(), courseDescription.getText(), Integer.parseInt(courseDuration.getText().trim()),
                        Double.parseDouble(courseMonthlyValue.getText().trim()), integerOrZero(courseSessions.getText()),
                        courseLevel.getValue(), courseObjectives.getText(), decimalOrZero(courseTeacherRate.getText())),
                "Curso agregado al catalogo.")) {
            courseCode.clear(); courseName.clear(); courseLanguage.clear(); courseDescription.clear();
            courseDuration.clear(); courseMonthlyValue.clear(); courseSessions.clear(); courseObjectives.clear(); courseTeacherRate.clear();
        }
    }

    private void saveEnrollment() {
        Object selectedService = enrollmentService.getValue();
        ServicioAdicional service = selectedService instanceof ServicioAdicional value ? value : null;
        double discount = decimalOrZero(enrollmentDiscount.getText()) / 100.0;
        if (runAction(() -> controller.crearMatricula(enrollmentStudent.getValue(), enrollmentCourse.getValue(),
                service, discount), "Matricula creada y total calculado.")) {
            enrollmentDiscount.clear();
        }
    }

    private void saveTeacher() {
        if (runAction(() -> controller.registrarProfesor(teacherId.getText(), teacherName.getText(),
                        teacherLanguage.getText(), teacherPhone.getText(), Double.parseDouble(teacherRate.getText().trim())),
                "Profesor registrado.")) {
            teacherId.clear(); teacherName.clear(); teacherLanguage.clear(); teacherPhone.clear(); teacherRate.clear();
        }
    }

    private void saveAssignment() {
        runAction(() -> controller.asignarProfesor(assignmentStudent.getValue(), assignmentCourse.getValue(),
                assignmentTeacher.getValue()), "Asignacion creada.");
    }

    private boolean runAction(Runnable action, String success) {
        try {
            action.run();
            status.setText(success);
            status.getStyleClass().remove("status-error");
            if (!status.getStyleClass().contains("status-success")) status.getStyleClass().add("status-success");
            return true;
        } catch (NumberFormatException exception) {
            showError("Revisa los campos numericos antes de guardar.");
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        } finally {
            refreshAll();
        }
        return false;
    }

    private void showError(String message) {
        status.setText(message);
        status.getStyleClass().remove("status-success");
        if (!status.getStyleClass().contains("status-error")) status.getStyleClass().add("status-error");
    }

    private void refreshAll() {
        if (studentCount != null) studentCount.setText(Integer.toString(controller.cantidadEstudiantes()));
        if (courseCount != null) courseCount.setText(Integer.toString(controller.cantidadCursos()));
        if (enrollmentCount != null) enrollmentCount.setText(Integer.toString(controller.cantidadMatriculas()));
        if (incomeTotal != null) incomeTotal.setText(money(controller.ingresos()));
        if (dashboardEnrollments != null) dashboardEnrollments.getItems().setAll(controller.matriculas());
        if (studentsTable != null) studentsTable.getItems().setAll(controller.estudiantes());
        if (coursesTable != null) coursesTable.getItems().setAll(controller.cursos());
        if (enrollmentsTable != null) enrollmentsTable.getItems().setAll(controller.matriculas());
        if (teachersTable != null) teachersTable.getItems().setAll(controller.profesores());
        if (assignmentsTable != null) assignmentsTable.getItems().setAll(controller.asignaciones());
        enrollmentStudent.setItems(FXCollections.observableArrayList(controller.estudiantes()));
        enrollmentCourse.setItems(FXCollections.observableArrayList(controller.cursos()));
        List<Object> serviceChoices = new java.util.ArrayList<>();
        serviceChoices.add("Sin servicio adicional");
        serviceChoices.addAll(controller.servicios());
        enrollmentService.setItems(FXCollections.observableArrayList(serviceChoices));
        if (enrollmentService.getValue() == null) enrollmentService.getSelectionModel().selectFirst();
        assignmentStudent.setItems(FXCollections.observableArrayList(controller.estudiantes()));
        assignmentCourse.setItems(FXCollections.observableArrayList(controller.cursosPersonalizados()));
        assignmentTeacher.setItems(FXCollections.observableArrayList(controller.profesores()));
    }

    private void configureCombos() {
        enrollmentStudent.setConverter(converter(Estudiante::getNombreCompleto));
        assignmentStudent.setConverter(converter(Estudiante::getNombreCompleto));
        enrollmentCourse.setConverter(converter(Curso::getNombre));
        assignmentCourse.setConverter(converter(CursoPersonalizado::getNombre));
        assignmentTeacher.setConverter(converter(Profesor::getNombre));
        enrollmentService.setConverter(new StringConverter<>() {
            @Override public String toString(Object value) {
                return value instanceof ServicioAdicional service ? service.getNombre() : value == null ? "" : value.toString();
            }
            @Override public Object fromString(String value) { return value; }
        });
    }

    private <T> StringConverter<T> converter(Function<T, String> label) {
        return new StringConverter<>() {
            @Override public String toString(T value) { return value == null ? "" : label.apply(value); }
            @Override public T fromString(String value) { return null; }
        };
    }

    private Label metric(HBox parent, String label, String value, String caption) {
        Label valueLabel = styledLabel(value, "metric-value");
        Label labelLabel = styledLabel(label, "metric-label");
        Label captionLabel = styledLabel(caption, "metric-accent");
        VBox card = new VBox(9, labelLabel, valueLabel, captionLabel);
        card.getStyleClass().add("metric-card");
        parent.getChildren().add(card);
        return valueLabel;
    }

    private VBox pageContent(Node... children) {
        VBox page = new VBox(18, children);
        page.getStyleClass().add("content-area");
        return page;
    }

    private VBox formPanel(String title, Node... children) {
        VBox panel = new VBox(14);
        panel.setPrefWidth(330);
        panel.getStyleClass().add("surface");
        panel.getChildren().add(styledLabel(title, "section-title"));
        panel.getChildren().addAll(children);
        return panel;
    }

    private GridPane formGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(9);
        return grid;
    }

    private void addField(GridPane grid, int row, String label, Node control) {
        Label caption = styledLabel(label, "muted");
        grid.add(caption, 0, row * 2);
        grid.add(control, 0, row * 2 + 1);
        if (control instanceof Region region) region.setMaxWidth(Double.MAX_VALUE);
    }

    private VBox labeled(String label, Node control) {
        VBox wrapper = new VBox(4, styledLabel(label, "muted"), control);
        if (control instanceof Region region) region.setMaxWidth(Double.MAX_VALUE);
        return wrapper;
    }

    private Button actionButton(String text, String style, Runnable action) {
        Button button = new Button(text);
        button.getStyleClass().add(style);
        button.setOnAction(event -> action.run());
        return button;
    }

    private <T> TableColumn<T, String> column(String title, Function<T, String> value) {
        TableColumn<T, String> column = new TableColumn<>(title);
        column.setCellValueFactory(cell -> new javafx.beans.property.ReadOnlyStringWrapper(value.apply(cell.getValue())));
        return column;
    }

    private Label styledLabel(String text, String style) {
        Label label = new Label(text);
        label.getStyleClass().add(style);
        return label;
    }

    private String money(double value) {
        return currency.format(value);
    }

    private static TextField field(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        return field;
    }

    private static int integerOrZero(String value) {
        return value == null || value.isBlank() ? 0 : Integer.parseInt(value.trim());
    }

    private static double decimalOrZero(String value) {
        return value == null || value.isBlank() ? 0 : Double.parseDouble(value.trim());
    }
}