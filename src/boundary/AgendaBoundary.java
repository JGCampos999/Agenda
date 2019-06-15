package boundary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import control.AgendaControl;
import entidade.Agenda;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AgendaBoundary extends Application {
	private TextField txtId = new TextField();
	private TextField txtNome = new TextField();
	private TextField txtTfone = new TextField();
	private TextField txtNasc = new TextField();

	private Button btnAdd = new Button("Adicionar");
	private Button btnUpdt = new Button("Atualizar");
	private Button btnDel = new Button("Remover");
	private Button btnSch = new Button("Pesquisar");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private AgendaControl control = new AgendaControl();

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Agenda a = new Agenda();
		BorderPane painelPrincipal = new BorderPane();
		GridPane painelDisplay = new GridPane();
		GridPane painel2 = new GridPane();
		GridPane painelBotoes = new GridPane();

		painel2.setVgap(10);

		txtId.setText("");
		txtNome.setText("");
		txtTfone.setText("");
		txtNasc.setText("");

		txtId.setPrefWidth(300);
		txtNome.setPrefWidth(300);
		txtTfone.setPrefWidth(300);
		txtNasc.setPrefWidth(300);

		btnAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!txtId.getText().equals("") && control.searchListaID(Integer.parseInt(txtId.getText()))) {
					JOptionPane.showMessageDialog(null, "ID já utilizado");
				} else if (txtId.getText().equals("") || txtNome.getText().equals("") || txtTfone.getText().equals("")
						|| txtNasc.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				} else {
					try {
						a.setId(Integer.parseInt(txtId.getText()));
						a.setNome(txtNome.getText());
						a.setTelefone(txtTfone.getText());
						Date data = sdf.parse(txtNasc.getText());
						a.setNascimento(data);
						control.add(a);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				txtId.setText("");
				txtNome.setText("");
				txtTfone.setText("");
				txtNasc.setText("");
			}

		});
		btnUpdt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					a.setNome(txtNome.getText());
					a.setTelefone(txtTfone.getText());
					Date data = sdf.parse(txtNasc.getText());
					a.setNascimento(data);
					control.editFLista(Integer.parseInt(txtId.getText()), a);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
		btnDel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				control.deleteFLista(Integer.parseInt(txtId.getText()));
			}

		});
		btnSch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Agenda agnd = control.searchLista(txtNome.getText());
				if (agnd != null) {
					txtId.setText(Integer.toString(agnd.getId()));
					txtNasc.setText(sdf.format(agnd.getNascimento()));
					txtTfone.setText(agnd.getTelefone());
				}
			}

		});

		Label lblId = new Label("Id");
		Label lblNome = new Label("Nome");
		Label lblTel = new Label("Telefone");
		Label lblNasc = new Label("Nascimento");

		painel2.add(lblId, 0, 0);
		painel2.add(lblNome, 0, 1);
		painel2.add(lblTel, 0, 2);
		painel2.add(lblNasc, 0, 3);

		painelDisplay.add(txtId, 1, 0);
		painelDisplay.add(txtNome, 1, 1);
		painelDisplay.add(txtTfone, 1, 2);
		painelDisplay.add(txtNasc, 1, 3);

		btnAdd.setPrefWidth(150);
		btnUpdt.setPrefWidth(150);
		btnDel.setPrefWidth(150);
		btnSch.setPrefWidth(150);

		painelBotoes.add(btnAdd, 0, 0);
		painelBotoes.add(btnUpdt, 1, 0);
		painelBotoes.add(btnDel, 2, 0);
		painelBotoes.add(btnSch, 3, 0);
		painelPrincipal.setLeft(painel2);
		painelPrincipal.setRight(painelDisplay);
		painelPrincipal.setBottom(painelBotoes);

		control.carregaLista();

		Scene scn = new Scene(painelPrincipal);
		stage.setScene(scn);
		stage.setTitle("Agenda Eletrônica");
		stage.show();

	}

}