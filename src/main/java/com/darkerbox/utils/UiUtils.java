package com.darkerbox.utils;

import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.text.JTextComponent;
import java.awt.*;

public class UiUtils {

	public static void setPrompt(String promptText, JTextComponent textComponent){
		PromptSupport.setPrompt(promptText,textComponent);
	}

	public static Font getSerifFont(){
		return new Font("Serif",0,20);
	}

}
