package ru.yandex.practicum.filmorate.controller.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {

	@Override
	public void write(final JsonWriter jsonWriter, final LocalDate localDate) throws IOException {
		String toWrite = "";
		if (localDate != null)
			toWrite = localDate.toString();
		jsonWriter.value(toWrite);
	}

	@Override
	public LocalDate read(final JsonReader jsonReader) throws IOException {
		LocalDate localDate = null;
		String localDateString = jsonReader.nextString();
		if (!localDateString.isBlank())
			localDate = LocalDate.parse(localDateString);
		return localDate;
	}
}

