package de.devofvictory.invanimation.objects;

public enum AnimationType {
	
	FADE_IN_LEFT_TO_RIGHT("Von links nach rechts einfahren"), FADE_IN_TOP_TO_BUTTOM("Von oben nach unten einfahren"),
	RANDOM_SPREAD("Zufällig ausbreiten"), SLOT_FOR_SLOT("Der Reihe nach aufbauen"), GROWING_BARS("Wachsene Balken"), SPIRAL("Spiralenförmig aufbauen");
	
	private String description;

	AnimationType(final String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

}
