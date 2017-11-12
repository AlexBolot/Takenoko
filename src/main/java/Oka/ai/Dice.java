package Oka.ai;

import Oka.controler.DrawStack;
import Oka.model.Enums;
import Oka.model.plot.state.EnclosureState;
import Oka.model.plot.state.FertilizerState;
import Oka.model.plot.state.PondState;
import Oka.utils.Logger;

import java.util.Optional;
import java.util.Random;

public class Dice
{
    static void rollDice (AI ai)
    {
        Enums.State[] values = Enums.State.values();

        switch (new Random().nextInt(5) + 1)
        {
            case 1:
                Logger.printLine("Sunny");
                ai.getInventory().getActionHolder().sunWeather();
                break;

            case 2:
                Logger.printLine("Windy");
                ai.getInventory().getActionHolder().windWeather();
                break;

            case 3:
                Logger.printLine("Rainy");
                ai.placeBambooOnPlot();
                break;

            case 4:
                Logger.printLine("Lighting");
                if (ai.movePanda()) ai.getInventory().resetActionHolder();
                break;

            case 5:

                switch (values[new Random().nextInt(values.length - 1) + 1])
                {
                    case Pond:
                        Optional<PondState> optPond = DrawStack.getInstance().drawPondState();
                        optPond.ifPresent(pondState -> ai.getInventory().addPlotState(pondState));
                        break;

                    case Enclosure:
                        Optional<EnclosureState> optEnclosure = DrawStack.getInstance().drawEnclosureState();
                        optEnclosure.ifPresent(enclosureState -> ai.getInventory().addPlotState(enclosureState));
                        break;

                    case Fertilizer:
                        Optional<FertilizerState> optFertilizer = DrawStack.getInstance().drawFertilizerState();
                        optFertilizer.ifPresent(fertilizerState -> ai.getInventory().addPlotState(fertilizerState));
                        break;
                }

                Logger.printLine("Cloudy");
                Logger.printLine(ai.getName() + " a pioché aménagement grâce au dé météo : " + ai.getInventory().plotStates());
                break;

            case 6:
                rollDice(ai);
                //TODO A améliorer en fonction des objectifs de l'IA, je n'ai rien mis dans case 6 actuellement, et le dé
                //TODO va de 1 à 5 pour l'instant.
        }
    }
}
