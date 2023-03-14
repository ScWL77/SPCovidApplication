package application;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Data {
	private final StringProperty date;
	private final StringProperty temperature;
	private final StringProperty code;
	private final ObjectProperty<Image> image;
	

	
	public Data(String date, String temperature, String code, Image photo) {
		this.date = new SimpleStringProperty(date);
		this.temperature = new SimpleStringProperty(temperature);
		this.code = new SimpleStringProperty(code);
		this.image = new SimpleObjectProperty<Image>(photo);
	}
	

	public String getDate() {
		return date.get();
	}
	
	public void setDate(String value) {
		date.set(value);
	}
	
	public StringProperty dateProperty() {
		return date;
	}
	
	public String getTemperature() {
		return temperature.get();
	}
	
	public void setTemperature(String value) {
		temperature.set(value);
	}
	
	public StringProperty temperatureProperty() {
		return temperature;
	}
	
	public String getCode() {
		return code.get();
	}
	
	public void setCode(String value) {
		code.set(value);
	}
	
	public StringProperty codeProperty() {
		return code;
	}

	public Object getImage() {
		return image.get();
	}
	
	public void setImage(Image value) {
		image.set(value);
	}
	
	public ObjectProperty<Image> imageProperty() {
		return image;
	}
}

