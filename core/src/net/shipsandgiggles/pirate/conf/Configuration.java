package net.shipsandgiggles.pirate.conf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import net.shipsandgiggles.pirate.entity.Ship;
import net.shipsandgiggles.pirate.screen.impl.GameScreen;

public class Configuration {

	/**global configs for variables commonly used */

	public static final Skin SKIN = new Skin(Gdx.files.internal("skin/comic-ui.json"));
	public static final float PIXEL_PER_METER = 1f; /** ppi to scale down the world*/
	public static final short Cat_Player = 1; /**cats are just categories to tell the bodies what to interact with */
	public static final short Cat_walls = 2;
	public static final short Cat_Enemy = 4;
	public static final short Cat_College = 8;
	public static final World world = GameScreen.world; /** the world*/
	public static final Label SPACER_LABEL = new Label(" ", Configuration.SKIN);

}