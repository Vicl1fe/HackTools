package com.darkerbox.utils;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.text.JTextComponent;

public class UiUtils {

	public static void setPrompt(String promptText, JTextComponent textComponent){
		PromptSupport.setPrompt(promptText,textComponent);
	}


}
