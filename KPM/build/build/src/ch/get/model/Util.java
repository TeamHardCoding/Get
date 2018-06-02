package ch.get.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import ch.get.MainApp;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;

public class Util {
	
	private DirectoryChooser directoryChooser;
	private File selDir;
	private Writer savFile;
	private BufferedWriter bw;
	private String savPath;
	private String savDateTime;
	private MainApp mainApp;
	private LocalDateTime stdTime;
	private DateTimeFormatter dtf;
	
	public Util() {
		// TODO Auto-generated constructor stub
		dtf = DateTimeFormatter.ofPattern("YYMMdd-hhmmss");
	}
	
	public void saveKPM(ObservableList<String> log)
	{
		try
		{
			directoryChooser = new DirectoryChooser();
			selDir = directoryChooser.showDialog(mainApp.getMainStage());
			savPath = selDir.getPath();
			
			if(savPath.isEmpty())
			{
				throw new Exception();
			}
			else
			{
				stdTime = LocalDateTime.now();
				savDateTime = stdTime.format(dtf);
				savFile = new FileWriter(savPath+"/"+("KPM sav "+savDateTime)+".txt");
				bw = new BufferedWriter(savFile);
				
				Iterator<String> itr = log.iterator();
				
				while(itr.hasNext())
				{
					bw.write(itr.next()+"\r\n");
				}
				
				bw.flush();
				savFile.close();
				bw.close();
			}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
}
