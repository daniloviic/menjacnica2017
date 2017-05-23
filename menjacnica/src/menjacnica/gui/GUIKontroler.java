package menjacnica.gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	
	private static MenjacnicaGUI glavniProzor;
	private static MenjacnicaInterface menjacnica;
	private static DodajKursGUI dodajKurs;
	private static IzvrsiZamenuGUI izvrsiZamenu;
	private static ObrisiKursGUI obrisiKurs;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					menjacnica = new Menjacnica();
					glavniProzor = new MenjacnicaGUI();
					glavniProzor.setVisible(true);
					glavniProzor.setLocationRelativeTo(null);
					glavniProzor.addWindowListener(new WindowAdapter() {
						
						public void windowClosing(WindowEvent e){
							ugasiAplikaciju();
						}

						
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	
	public static void ugasiAplikaciju() {
		int odgovor = JOptionPane.showConfirmDialog(glavniProzor, "Da li ste sigurni da zelite da ugasite aplikaciju ? ",
												"Gasenje", JOptionPane.YES_NO_OPTION);
		if(odgovor== JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	public static void prikaziDodajKursGUI() {
		dodajKurs = new DodajKursGUI();
		dodajKurs.setVisible(true);
		dodajKurs.setLocationRelativeTo(glavniProzor);
	}
	
	public static void prikaziObrisiKursGUI(Valuta valuta) {
		
		obrisiKurs = new ObrisiKursGUI(valuta);
		obrisiKurs.setVisible(true);
		obrisiKurs.setLocationRelativeTo(glavniProzor);
		
	}
	
	public static void prikaziIzvrsiZamenuGUI(Valuta valuta){
		izvrsiZamenu = new IzvrsiZamenuGUI(valuta);
		izvrsiZamenu.setLocationRelativeTo(glavniProzor);
		izvrsiZamenu.setVisible(true);
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(glavniProzor,
				"Autor: Milos Danilovic, Verzija 2.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void ucitajIzFajla(){
		
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(glavniProzor);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				glavniProzor.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(glavniProzor);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(glavniProzor, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static  LinkedList<Valuta> vratiKursnuListu() {
		return menjacnica.vratiKursnuListu();
	}
	
	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra, String prodajni, String kupovni, String srednji){
		try {
			
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(Double.parseDouble(prodajni));
			valuta.setKupovni(Double.parseDouble(kupovni));
			valuta.setSrednji(Double.parseDouble(srednji));
			
			menjacnica.dodajValutu(valuta);
			glavniProzor.prikaziSveValute();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(glavniProzor, e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static double izvrsiZamenuValute(Valuta valuta, double iznos, boolean selected) {
		return menjacnica.izvrsiTransakciju(valuta, selected, iznos);
	}
	
	public static void obrisiValutu(Valuta valuta) {
		menjacnica.obrisiValutu(valuta);
		glavniProzor.prikaziSveValute();
		
	}
}
