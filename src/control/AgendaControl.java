package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import entidade.Agenda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AgendaControl {
	private List<Agenda> lista = new ArrayList<>();
	private ObservableList<Agenda> dataLista = FXCollections.observableArrayList();
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	// FileReader fRD = new FileReader("Agenda.txt");
	// FileWriter fWT = new FileWriter("Agenda.txt");
	// BufferedReader bRD = new BufferedReader(new FileReader("Agenda.txt")):
	// BufferedWriter bWT = new BufferedWriter(new FileWriter("Agenda.txt"));

	public void add(Agenda a) {
		lista.add(a);
		dataLista.clear();
		dataLista.addAll(lista);
		try {
			BufferedWriter bWT = new BufferedWriter(new FileWriter("Agenda.txt", true));
			StringBuffer buffer = new StringBuffer();
			buffer.append(a.getId() + ";");
			buffer.append(a.getNome() + ";");
			buffer.append(a.getTelefone() + ";");
			String data = sdf.format(a.getNascimento());
			buffer.append(data + "\r\n");
			bWT.write(buffer.toString());
			bWT.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregaLista() {
		try {
			File file = new File("Agenda.txt");
			if (!file.exists()) {
				file.createNewFile();
			}

			@SuppressWarnings("resource")
			BufferedReader bRD = new BufferedReader(new FileReader(file));
			String linha;
			while ((linha = bRD.readLine()) != null) {
				Agenda a = new Agenda();
				String vet[] = linha.split(";");
				a.setId(Integer.parseInt(vet[0]));
				a.setNome(vet[1]);
				a.setTelefone(vet[2]);
				Date data = sdf.parse(vet[3]);
				a.setNascimento(data);
				lista.add(a);
				vet = linha.split(";");
				dataLista.clear();
				dataLista.addAll(lista);
			}
			System.out.println(dataLista);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteFLista(int ID) {
		if (!searchListaID(ID)) {
			JOptionPane.showMessageDialog(null, "Não há na lista");
		} else {
			for (Agenda a : lista) {
				if (a.getId() == ID) {
					lista.remove(a);
					dataLista.clear();
					dataLista.addAll(lista);
					File fl = new File("Agenda.txt");
					fl.delete();
					add(a);
				}
			}
		}
	}

	public void editFLista(int ID, Agenda a) {
		if (!searchListaID(ID)) {
			JOptionPane.showMessageDialog(null, "Não há na lista");
		} else {
			for (Agenda agd : lista) {
				if (agd.getId() == ID) {
					lista.set(lista.indexOf(agd), a);
					dataLista.clear();
					dataLista.addAll(lista);
					File fl = new File("Agenda.txt");
					fl.delete();
					add(a);
				}
			}
		}
	}

	public Agenda searchLista(String nome) {
		for (Agenda a : lista) {
			if (a.getNome().contains(nome) && nome != "") {
				return a;
			}
		}
		return null;
	}

	public boolean searchListaID(int ID) {
		for (Agenda a : lista) {
			if (a.getId() == ID) {
				return true;
			}
		}
		return false;
	}

	public ObservableList<Agenda> getDataList() {
		return dataLista;
	}

	public void setDataLista(ObservableList<Agenda> dataLista) {
		this.dataLista = dataLista;
	}
}