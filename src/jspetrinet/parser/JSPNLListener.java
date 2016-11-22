// Generated from JSPNL.g4 by ANTLR 4.5.3

package jspetrinet.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link JSPNLParser}.
 */
public interface JSPNLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(JSPNLParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(JSPNLParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(JSPNLParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(JSPNLParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(JSPNLParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(JSPNLParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#node_declaration}.
	 * @param ctx the parse tree
	 */
	void enterNode_declaration(JSPNLParser.Node_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#node_declaration}.
	 * @param ctx the parse tree
	 */
	void exitNode_declaration(JSPNLParser.Node_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#arc_declaration}.
	 * @param ctx the parse tree
	 */
	void enterArc_declaration(JSPNLParser.Arc_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#arc_declaration}.
	 * @param ctx the parse tree
	 */
	void exitArc_declaration(JSPNLParser.Arc_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#option_list}.
	 * @param ctx the parse tree
	 */
	void enterOption_list(JSPNLParser.Option_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#option_list}.
	 * @param ctx the parse tree
	 */
	void exitOption_list(JSPNLParser.Option_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#option_value}.
	 * @param ctx the parse tree
	 */
	void enterOption_value(JSPNLParser.Option_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#option_value}.
	 * @param ctx the parse tree
	 */
	void exitOption_value(JSPNLParser.Option_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#assign_expression}.
	 * @param ctx the parse tree
	 */
	void enterAssign_expression(JSPNLParser.Assign_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#assign_expression}.
	 * @param ctx the parse tree
	 */
	void exitAssign_expression(JSPNLParser.Assign_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(JSPNLParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(JSPNLParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#function_expression}.
	 * @param ctx the parse tree
	 */
	void enterFunction_expression(JSPNLParser.Function_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#function_expression}.
	 * @param ctx the parse tree
	 */
	void exitFunction_expression(JSPNLParser.Function_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#arg_list}.
	 * @param ctx the parse tree
	 */
	void enterArg_list(JSPNLParser.Arg_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#arg_list}.
	 * @param ctx the parse tree
	 */
	void exitArg_list(JSPNLParser.Arg_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#arg_value}.
	 * @param ctx the parse tree
	 */
	void enterArg_value(JSPNLParser.Arg_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#arg_value}.
	 * @param ctx the parse tree
	 */
	void exitArg_value(JSPNLParser.Arg_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#ntoken_expression}.
	 * @param ctx the parse tree
	 */
	void enterNtoken_expression(JSPNLParser.Ntoken_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#ntoken_expression}.
	 * @param ctx the parse tree
	 */
	void exitNtoken_expression(JSPNLParser.Ntoken_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#enable_expression}.
	 * @param ctx the parse tree
	 */
	void enterEnable_expression(JSPNLParser.Enable_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#enable_expression}.
	 * @param ctx the parse tree
	 */
	void exitEnable_expression(JSPNLParser.Enable_expressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link JSPNLParser#literal_expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_expression(JSPNLParser.Literal_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link JSPNLParser#literal_expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_expression(JSPNLParser.Literal_expressionContext ctx);
}