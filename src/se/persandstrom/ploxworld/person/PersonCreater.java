package se.persandstrom.ploxworld.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import se.persandstrom.ploxworld.common.Rand;
import se.persandstrom.ploxworld.locations.Location;
import se.persandstrom.ploxworld.main.World;

public class PersonCreater {

	private final List<String> maleNames = new ArrayList<>(Arrays.asList("Jakob Lind", "Mikael Emilsson", "Per Sandstr�m", "Jacob Rask", "Hoyt Tyrrell",
			"Avery Koerner", "Maxwell Avison", "Britt Wakeman", "Porter Jackstadt", "Damien Severt", "Nolan Alers", "Harlan Ducote", "Thaddeus Sanghvi",
			"Gavin Gulsvig", "Craige Lera", "Carly Jones", "Jery Rownez", "Richua Brobarn", "Phawne Breson", "Billie Mitchy", "Gary Flewill", "Roldy Rezal",
			"Randy Theson", "Edwam Thallee", "Malcolm McCree", "Silas Lloyd", "Thaddeus Clark", "Gabriel Law", "Morris McNevin", "Nelson Clarke", "Harland Anderson",
			"Bailey Cox", "Rufus Hunter", "Woodrow Wilson", "Edison Gammon", "Chet Zephyr", "Scotty Vanlaere", "Walton Sharpey", "Darius Chalet", "Damien Holtz",
			"Broderick Zahra", "Edison Cayne", "Darius Paulsen", "Rory Alder", "Vyna Ecken", "Tarsi Javand", "Iakaf Berand", "Kesi Andar", "Rharo Tillie", "Rix Avar",
			"Vyna Melne", "Nej Hamne", "Hoan Horne", "Eucer Yougher", "Jackenn Lopet", "Juanio Coopow", "Tephy Griffin", "Denne Aller", "Ronio Lore", "Shawne Gerson",
			"Dave Brobarn", "Jamy Reeders", "Randy Perra"));
	private final List<String> femaleNames = new ArrayList<>(Arrays.asList("Kimby Bennes", "Annies Kere", "Dianie Leray", "Dithy Dera", "Terea Liamson", "Mara Nelson",
			"Kara Scarte", "Stinie Ampbes", "Kathy Belley", "Jeana Pere", "Lille Carte", "Joana Ampbes", "Arthah Jonez", "Erlyn Campbak", "Jeana Perray", "Irgis Jackson",
			"Chelle Hellee", "Janie Helly", "Licia Pete", "Verly", "Henders", "Dorie Wisand", "Jane Hillee", "Margel Hezal", "", "ana Wisimm", "Margin Morray",
			"Kathly Wooder", "Louise Willey", "Mely Garce", "Margin Coopark", "Athen Derson", "Diana Helly", "Donne Woodiaz", "Coley Warders", "Kathy Tayly",
			"Loria Parkell", "Enen Clexand", "Betty Parker", "Lora Sterson", "Mela Milley", "Elin Carte", "Amira Severt", "Lael Yueh", "Collene Breton",
			"Charlena Harcrow", "Laree Holbach", "Tandra Castiglione", "Lilliam Erikson", "Robena Mika", "Jinny Nakada", "Maris Zahra", "Sandra Cherniky", "Kara Baseva",
			"Anabi Gorskovsky", "Dara Khepora", "Bruna Beline", "Idani Garina", "Nessa Gerschiko", "Vilma Renky", "Yeksaga Boveli", "Alyale Rinova"));

	private final World world;

	public PersonCreater(World world) {
		this.world = world;
	}

	public List<Person> createPersons(int number, PersonalityType personalityType) {
		List<Person> persons = new ArrayList<>();
		while (number-- > 0) {
			persons.add(createPerson(personalityType));
		}
		Collections.sort(persons);
		return persons;
	}

	public Person createPerson(PersonalityType personalityType) {
		String name = getRandomName();
		Location location = Rand.getRandom(world.getLocations());
		return new Person(personalityType.getAi(), personalityType.getPersonality(), name, location);
	}

	private String getRandomName() {
		List<String> names;
		if (Rand.bool()) {
			names = maleNames;
		} else {
			names = femaleNames;
		}
		int i = Rand.bound(names.size());
		String name = names.get(i);
		names.remove(i);
		return name;
	}

}
