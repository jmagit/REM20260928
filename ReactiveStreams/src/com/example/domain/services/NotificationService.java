package com.example.domain.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class NotificationService extends SubmissionPublisher<NotificationService.Notification> {
	public static enum Types {
		ERROR, WARN, INFO, LOG
	}

	public static record Notification(String message, Types type) {
		public Notification(String message) {
			this(message, Types.ERROR);
		}

		public Notification(String message, Types type) {
			if (message == null || message.isBlank())
				throw new IllegalArgumentException("El mensaje no puede ser nulo o estar en blanco");
			if (type == null)
				throw new IllegalArgumentException("El tipo es obligatorio");
			this.message = message;
			this.type = type;
		}
	}

	private List<Notification> list = new ArrayList<Notification>();
	private Consumer<Notification> output;

	public void setOutput(Consumer<Notification> output) {
		this.output = output;
	}

	public List<Notification> getNotifications() {
		return Collections.unmodifiableList(list);
	}

	public void add(String message, Types type) {
		if (message == null || message.isBlank())
			throw new IllegalArgumentException();
		var msg = new Notification(message, type);
		list.add(msg);
		if (output != null)
			output.accept(msg);
		this.submit(msg);
	}

	public void add(String message) {
		add(message, Types.ERROR);
	}

	public void remove(int index) {
		if (index < 0 || index >= list.size())
			throw new IndexOutOfBoundsException();
		list.remove(index);
	}

	public void clear() {
		list.clear();
	}

	public boolean hasNotifications() {
		return list.size() > 0;
	}

	public boolean hasErrors() {
		return list.stream().anyMatch(item -> item.type() == Types.ERROR);
	}
}
