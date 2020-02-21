# Optimization and Uncertainty Project 1
by Caleb Eurich, Tenzin Choezin, Elliot Ketchel.

All variables such as positive and negative rewards, Step costs, Probabilities of going forward vs
clockwise or counterclockwise are specified in the top of the MDP function. There is nothing to parse
input from the command line yet, so the character to specify which solution technique to use so it also
needs to be specified in the parameters at the top of the mdp file.

To see the message below call "java mdp help"
When using arguments specified in function run without any arguments.
When inputting arguments from command line use following format:
    java mdp Discount[double] Max_Error[double] key_loss_probability[double]
    pos_reward[double] neg_reward[double] step_cost[double] solution_technique[char]
---------- common values / value options ----------
Discount: between 1 and 0, otherwise values go to infinity
Max_Error: often close to 0, higher value will mean fewer iterations
Key_loss_probability: usually .5, must be between 1 and 0
pos_reward: anything positive, bigger value will overshadow negative reward and step costs
neg_reward: anything negative, bigger value will overshadow positive reward
step_cost: generally negative, if positive terminal states will be avoided
solution_technique: 'v' for value iteration, 'p' for policy iteration, 'q' for Q-learning