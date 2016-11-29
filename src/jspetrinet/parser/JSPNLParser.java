// Generated from JSPNL.g4 by ANTLR 4.5.3

package jspetrinet.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JSPNLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, LOGICAL=34, ID=35, INT=36, FLOAT=37, STRING=38, NEWLINE=39, 
		WS=40, LINE_COMMENT=41, BLOCK_COMMENT=42;
	public static final int
		RULE_prog = 0, RULE_statement = 1, RULE_simple_block = 2, RULE_declaration = 3, 
		RULE_node_declaration = 4, RULE_arc_declaration = 5, RULE_assert_declaration = 6, 
		RULE_option_list = 7, RULE_option_value = 8, RULE_simple = 9, RULE_assign_expression = 10, 
		RULE_expression = 11, RULE_function_expression = 12, RULE_arg_list = 13, 
		RULE_arg_value = 14, RULE_ntoken_expression = 15, RULE_enable_expression = 16, 
		RULE_literal_expression = 17;
	public static final String[] ruleNames = {
		"prog", "statement", "simple_block", "declaration", "node_declaration", 
		"arc_declaration", "assert_declaration", "option_list", "option_value", 
		"simple", "assign_expression", "expression", "function_expression", "arg_list", 
		"arg_value", "ntoken_expression", "enable_expression", "literal_expression"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'{'", "'}'", "'place'", "'('", "')'", "'trans'", "'arc'", "'iarc'", 
		"'oarc'", "'harc'", "'to'", "'assert'", "','", "'='", "':='", "'!'", "'+'", 
		"'-'", "'*'", "'/'", "'div'", "'mod'", "'<'", "'<='", "'>'", "'>='", "'=='", 
		"'!='", "'&&'", "'||'", "'ifelse'", "'#'", "'?'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, "LOGICAL", 
		"ID", "INT", "FLOAT", "STRING", "NEWLINE", "WS", "LINE_COMMENT", "BLOCK_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "JSPNL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JSPNLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public List<TerminalNode> NEWLINE() { return getTokens(JSPNLParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(JSPNLParser.NEWLINE, i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitProg(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__11) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING) | (1L << NEWLINE))) != 0)) {
				{
				{
				setState(37);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__11) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
					{
					setState(36);
					statement();
					}
				}

				setState(39);
				match(NEWLINE);
				}
				}
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public SimpleContext simple() {
			return getRuleContext(SimpleContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(47);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(45);
				declaration();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(46);
				simple();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Simple_blockContext extends ParserRuleContext {
		public List<TerminalNode> NEWLINE() { return getTokens(JSPNLParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(JSPNLParser.NEWLINE, i);
		}
		public List<SimpleContext> simple() {
			return getRuleContexts(SimpleContext.class);
		}
		public SimpleContext simple(int i) {
			return getRuleContext(SimpleContext.class,i);
		}
		public Simple_blockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterSimple_block(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitSimple_block(this);
		}
	}

	public final Simple_blockContext simple_block() throws RecognitionException {
		Simple_blockContext _localctx = new Simple_blockContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_simple_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(49);
				match(NEWLINE);
				}
				}
				setState(54);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(55);
			match(T__0);
			setState(57);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
				{
				setState(56);
				simple();
				}
			}

			setState(65);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==NEWLINE) {
				{
				{
				setState(59);
				match(NEWLINE);
				setState(61);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__3) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << LOGICAL) | (1L << ID) | (1L << INT) | (1L << FLOAT) | (1L << STRING))) != 0)) {
					{
					setState(60);
					simple();
					}
				}

				}
				}
				setState(67);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(68);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public Node_declarationContext node_declaration() {
			return getRuleContext(Node_declarationContext.class,0);
		}
		public Arc_declarationContext arc_declaration() {
			return getRuleContext(Arc_declarationContext.class,0);
		}
		public Assert_declarationContext assert_declaration() {
			return getRuleContext(Assert_declarationContext.class,0);
		}
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitDeclaration(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_declaration);
		try {
			setState(73);
			switch (_input.LA(1)) {
			case T__2:
			case T__5:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(70);
				node_declaration();
				}
				break;
			case T__6:
			case T__7:
			case T__8:
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(71);
				arc_declaration();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 3);
				{
				setState(72);
				assert_declaration();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Node_declarationContext extends ParserRuleContext {
		public Token node;
		public Token id;
		public List<TerminalNode> ID() { return getTokens(JSPNLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JSPNLParser.ID, i);
		}
		public Option_listContext option_list() {
			return getRuleContext(Option_listContext.class,0);
		}
		public Simple_blockContext simple_block() {
			return getRuleContext(Simple_blockContext.class,0);
		}
		public Node_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_node_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterNode_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitNode_declaration(this);
		}
	}

	public final Node_declarationContext node_declaration() throws RecognitionException {
		Node_declarationContext _localctx = new Node_declarationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_node_declaration);
		int _la;
		try {
			setState(105);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				((Node_declarationContext)_localctx).node = match(T__2);
				setState(76);
				((Node_declarationContext)_localctx).id = match(ID);
				setState(81);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(77);
					match(T__3);
					setState(78);
					option_list();
					setState(79);
					match(T__4);
					}
				}

				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(83);
				((Node_declarationContext)_localctx).node = match(T__5);
				setState(84);
				((Node_declarationContext)_localctx).id = match(ID);
				setState(89);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(85);
					match(T__3);
					setState(86);
					option_list();
					setState(87);
					match(T__4);
					}
				}

				setState(92);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
				case 1:
					{
					setState(91);
					simple_block();
					}
					break;
				}
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(94);
				((Node_declarationContext)_localctx).node = match(ID);
				setState(95);
				((Node_declarationContext)_localctx).id = match(ID);
				setState(100);
				_la = _input.LA(1);
				if (_la==T__3) {
					{
					setState(96);
					match(T__3);
					setState(97);
					option_list();
					setState(98);
					match(T__4);
					}
				}

				setState(103);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					setState(102);
					simple_block();
					}
					break;
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arc_declarationContext extends ParserRuleContext {
		public Token type;
		public Token srcName;
		public Token destName;
		public List<TerminalNode> ID() { return getTokens(JSPNLParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(JSPNLParser.ID, i);
		}
		public Option_listContext option_list() {
			return getRuleContext(Option_listContext.class,0);
		}
		public Arc_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arc_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterArc_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitArc_declaration(this);
		}
	}

	public final Arc_declarationContext arc_declaration() throws RecognitionException {
		Arc_declarationContext _localctx = new Arc_declarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_arc_declaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			((Arc_declarationContext)_localctx).type = _input.LT(1);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9))) != 0)) ) {
				((Arc_declarationContext)_localctx).type = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(108);
			((Arc_declarationContext)_localctx).srcName = match(ID);
			setState(109);
			match(T__10);
			setState(110);
			((Arc_declarationContext)_localctx).destName = match(ID);
			setState(115);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(111);
				match(T__3);
				setState(112);
				option_list();
				setState(113);
				match(T__4);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assert_declarationContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Simple_blockContext simple_block() {
			return getRuleContext(Simple_blockContext.class,0);
		}
		public Assert_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assert_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterAssert_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitAssert_declaration(this);
		}
	}

	public final Assert_declarationContext assert_declaration() throws RecognitionException {
		Assert_declarationContext _localctx = new Assert_declarationContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_assert_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			match(T__11);
			setState(120);
			switch (_input.LA(1)) {
			case T__3:
			case T__15:
			case T__16:
			case T__17:
			case T__30:
			case T__31:
			case T__32:
			case LOGICAL:
			case ID:
			case INT:
			case FLOAT:
			case STRING:
				{
				setState(118);
				expression(0);
				}
				break;
			case T__0:
			case NEWLINE:
				{
				setState(119);
				simple_block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Option_listContext extends ParserRuleContext {
		public Option_valueContext option_value() {
			return getRuleContext(Option_valueContext.class,0);
		}
		public List<Option_listContext> option_list() {
			return getRuleContexts(Option_listContext.class);
		}
		public Option_listContext option_list(int i) {
			return getRuleContext(Option_listContext.class,i);
		}
		public Option_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterOption_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitOption_list(this);
		}
	}

	public final Option_listContext option_list() throws RecognitionException {
		Option_listContext _localctx = new Option_listContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_option_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			option_value();
			setState(127);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(123);
					match(T__12);
					setState(124);
					option_list();
					}
					} 
				}
				setState(129);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Option_valueContext extends ParserRuleContext {
		public Assign_expressionContext assign_expression() {
			return getRuleContext(Assign_expressionContext.class,0);
		}
		public Option_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterOption_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitOption_value(this);
		}
	}

	public final Option_valueContext option_value() throws RecognitionException {
		Option_valueContext _localctx = new Option_valueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_option_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			assign_expression();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SimpleContext extends ParserRuleContext {
		public Assign_expressionContext assign_expression() {
			return getRuleContext(Assign_expressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SimpleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterSimple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitSimple(this);
		}
	}

	public final SimpleContext simple() throws RecognitionException {
		SimpleContext _localctx = new SimpleContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_simple);
		try {
			setState(134);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				assign_expression();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(133);
				expression(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Assign_expressionContext extends ParserRuleContext {
		public int type;
		public Token id;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Ntoken_expressionContext ntoken_expression() {
			return getRuleContext(Ntoken_expressionContext.class,0);
		}
		public Assign_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterAssign_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitAssign_expression(this);
		}
	}

	public final Assign_expressionContext assign_expression() throws RecognitionException {
		Assign_expressionContext _localctx = new Assign_expressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assign_expression);
		try {
			setState(151);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				((Assign_expressionContext)_localctx).id = match(ID);
				setState(137);
				match(T__13);
				setState(138);
				expression(0);
				 ((Assign_expressionContext)_localctx).type =  1; 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(141);
				((Assign_expressionContext)_localctx).id = match(ID);
				setState(142);
				match(T__14);
				setState(143);
				expression(0);
				 ((Assign_expressionContext)_localctx).type =  2; 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(146);
				ntoken_expression();
				setState(147);
				match(T__13);
				setState(148);
				expression(0);
				 ((Assign_expressionContext)_localctx).type =  3; 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public int type;
		public Token op;
		public Token id;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public Function_expressionContext function_expression() {
			return getRuleContext(Function_expressionContext.class,0);
		}
		public Ntoken_expressionContext ntoken_expression() {
			return getRuleContext(Ntoken_expressionContext.class,0);
		}
		public Enable_expressionContext enable_expression() {
			return getRuleContext(Enable_expressionContext.class,0);
		}
		public Literal_expressionContext literal_expression() {
			return getRuleContext(Literal_expressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				{
				setState(154);
				((ExpressionContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__16) | (1L << T__17))) != 0)) ) {
					((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(155);
				expression(14);
				 ((ExpressionContext)_localctx).type =  1; 
				}
				break;
			case 2:
				{
				setState(158);
				((ExpressionContext)_localctx).op = match(T__30);
				setState(159);
				match(T__3);
				setState(160);
				expression(0);
				setState(161);
				match(T__12);
				setState(162);
				expression(0);
				setState(163);
				match(T__12);
				setState(164);
				expression(0);
				setState(165);
				match(T__4);
				 ((ExpressionContext)_localctx).type =  8; 
				}
				break;
			case 3:
				{
				setState(168);
				function_expression();
				 ((ExpressionContext)_localctx).type =  9; 
				}
				break;
			case 4:
				{
				setState(171);
				ntoken_expression();
				 ((ExpressionContext)_localctx).type =  10; 
				}
				break;
			case 5:
				{
				setState(174);
				enable_expression();
				 ((ExpressionContext)_localctx).type =  14; 
				}
				break;
			case 6:
				{
				setState(177);
				literal_expression();
				 ((ExpressionContext)_localctx).type =  11; 
				}
				break;
			case 7:
				{
				setState(180);
				((ExpressionContext)_localctx).id = match(ID);
				 ((ExpressionContext)_localctx).type =  12; 
				}
				break;
			case 8:
				{
				setState(182);
				match(T__3);
				setState(183);
				expression(0);
				setState(184);
				match(T__4);
				 ((ExpressionContext)_localctx).type =  13; 
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(221);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(219);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(189);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(190);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(191);
						expression(14);
						 ((ExpressionContext)_localctx).type =  2; 
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(194);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(195);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__16 || _la==T__17) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(196);
						expression(13);
						 ((ExpressionContext)_localctx).type =  3; 
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(199);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(200);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__23) | (1L << T__24) | (1L << T__25))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(201);
						expression(12);
						 ((ExpressionContext)_localctx).type =  4; 
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(204);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(205);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==T__26 || _la==T__27) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(206);
						expression(11);
						 ((ExpressionContext)_localctx).type =  5; 
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(209);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(210);
						((ExpressionContext)_localctx).op = match(T__28);
						setState(211);
						expression(10);
						 ((ExpressionContext)_localctx).type =  6; 
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(214);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(215);
						((ExpressionContext)_localctx).op = match(T__29);
						setState(216);
						expression(9);
						 ((ExpressionContext)_localctx).type =  7; 
						}
						break;
					}
					} 
				}
				setState(223);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Function_expressionContext extends ParserRuleContext {
		public Token id;
		public Arg_listContext arg_list() {
			return getRuleContext(Arg_listContext.class,0);
		}
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Function_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterFunction_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitFunction_expression(this);
		}
	}

	public final Function_expressionContext function_expression() throws RecognitionException {
		Function_expressionContext _localctx = new Function_expressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_function_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			((Function_expressionContext)_localctx).id = match(ID);
			setState(225);
			match(T__3);
			setState(226);
			arg_list();
			setState(227);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arg_listContext extends ParserRuleContext {
		public Arg_valueContext arg_value() {
			return getRuleContext(Arg_valueContext.class,0);
		}
		public List<Arg_listContext> arg_list() {
			return getRuleContexts(Arg_listContext.class);
		}
		public Arg_listContext arg_list(int i) {
			return getRuleContext(Arg_listContext.class,i);
		}
		public Arg_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterArg_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitArg_list(this);
		}
	}

	public final Arg_listContext arg_list() throws RecognitionException {
		Arg_listContext _localctx = new Arg_listContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_arg_list);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			arg_value();
			setState(234);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(230);
					match(T__12);
					setState(231);
					arg_list();
					}
					} 
				}
				setState(236);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Arg_valueContext extends ParserRuleContext {
		public ExpressionContext val;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public Arg_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterArg_value(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitArg_value(this);
		}
	}

	public final Arg_valueContext arg_value() throws RecognitionException {
		Arg_valueContext _localctx = new Arg_valueContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_arg_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			((Arg_valueContext)_localctx).val = expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ntoken_expressionContext extends ParserRuleContext {
		public Token id;
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Ntoken_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ntoken_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterNtoken_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitNtoken_expression(this);
		}
	}

	public final Ntoken_expressionContext ntoken_expression() throws RecognitionException {
		Ntoken_expressionContext _localctx = new Ntoken_expressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ntoken_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(T__31);
			setState(240);
			((Ntoken_expressionContext)_localctx).id = match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Enable_expressionContext extends ParserRuleContext {
		public Token id;
		public TerminalNode ID() { return getToken(JSPNLParser.ID, 0); }
		public Enable_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enable_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterEnable_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitEnable_expression(this);
		}
	}

	public final Enable_expressionContext enable_expression() throws RecognitionException {
		Enable_expressionContext _localctx = new Enable_expressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_enable_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			match(T__32);
			setState(243);
			((Enable_expressionContext)_localctx).id = match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Literal_expressionContext extends ParserRuleContext {
		public int type;
		public Token val;
		public TerminalNode INT() { return getToken(JSPNLParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(JSPNLParser.FLOAT, 0); }
		public TerminalNode LOGICAL() { return getToken(JSPNLParser.LOGICAL, 0); }
		public TerminalNode STRING() { return getToken(JSPNLParser.STRING, 0); }
		public Literal_expressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).enterLiteral_expression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof JSPNLListener ) ((JSPNLListener)listener).exitLiteral_expression(this);
		}
	}

	public final Literal_expressionContext literal_expression() throws RecognitionException {
		Literal_expressionContext _localctx = new Literal_expressionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_literal_expression);
		try {
			setState(253);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(245);
				((Literal_expressionContext)_localctx).val = match(INT);
				 ((Literal_expressionContext)_localctx).type =  1; 
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(247);
				((Literal_expressionContext)_localctx).val = match(FLOAT);
				 ((Literal_expressionContext)_localctx).type =  2; 
				}
				break;
			case LOGICAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(249);
				((Literal_expressionContext)_localctx).val = match(LOGICAL);
				 ((Literal_expressionContext)_localctx).type =  3; 
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(251);
				((Literal_expressionContext)_localctx).val = match(STRING);
				 ((Literal_expressionContext)_localctx).type =  4; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 11:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 13);
		case 1:
			return precpred(_ctx, 12);
		case 2:
			return precpred(_ctx, 11);
		case 3:
			return precpred(_ctx, 10);
		case 4:
			return precpred(_ctx, 9);
		case 5:
			return precpred(_ctx, 8);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3,\u0102\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\5\2(\n\2\3\2\7\2+\n\2\f\2\16\2.\13\2\3\3\3\3\5\3\62\n\3"+
		"\3\4\7\4\65\n\4\f\4\16\48\13\4\3\4\3\4\5\4<\n\4\3\4\3\4\5\4@\n\4\7\4B"+
		"\n\4\f\4\16\4E\13\4\3\4\3\4\3\5\3\5\3\5\5\5L\n\5\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\5\6T\n\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\\\n\6\3\6\5\6_\n\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\5\6g\n\6\3\6\5\6j\n\6\5\6l\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\5\7v\n\7\3\b\3\b\3\b\5\b{\n\b\3\t\3\t\3\t\7\t\u0080\n\t\f\t\16"+
		"\t\u0083\13\t\3\n\3\n\3\13\3\13\5\13\u0089\n\13\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u009a\n\f\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00be\n\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00de\n\r\f\r\16\r\u00e1"+
		"\13\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\7\17\u00eb\n\17\f\17\16"+
		"\17\u00ee\13\17\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\5\23\u0100\n\23\3\23\2\3\30\24\2\4\6\b\n\f\16"+
		"\20\22\24\26\30\32\34\36 \"$\2\b\3\2\t\f\3\2\22\24\3\2\25\30\3\2\23\24"+
		"\3\2\31\34\3\2\35\36\u0116\2,\3\2\2\2\4\61\3\2\2\2\6\66\3\2\2\2\bK\3\2"+
		"\2\2\nk\3\2\2\2\fm\3\2\2\2\16w\3\2\2\2\20|\3\2\2\2\22\u0084\3\2\2\2\24"+
		"\u0088\3\2\2\2\26\u0099\3\2\2\2\30\u00bd\3\2\2\2\32\u00e2\3\2\2\2\34\u00e7"+
		"\3\2\2\2\36\u00ef\3\2\2\2 \u00f1\3\2\2\2\"\u00f4\3\2\2\2$\u00ff\3\2\2"+
		"\2&(\5\4\3\2\'&\3\2\2\2\'(\3\2\2\2()\3\2\2\2)+\7)\2\2*\'\3\2\2\2+.\3\2"+
		"\2\2,*\3\2\2\2,-\3\2\2\2-\3\3\2\2\2.,\3\2\2\2/\62\5\b\5\2\60\62\5\24\13"+
		"\2\61/\3\2\2\2\61\60\3\2\2\2\62\5\3\2\2\2\63\65\7)\2\2\64\63\3\2\2\2\65"+
		"8\3\2\2\2\66\64\3\2\2\2\66\67\3\2\2\2\679\3\2\2\28\66\3\2\2\29;\7\3\2"+
		"\2:<\5\24\13\2;:\3\2\2\2;<\3\2\2\2<C\3\2\2\2=?\7)\2\2>@\5\24\13\2?>\3"+
		"\2\2\2?@\3\2\2\2@B\3\2\2\2A=\3\2\2\2BE\3\2\2\2CA\3\2\2\2CD\3\2\2\2DF\3"+
		"\2\2\2EC\3\2\2\2FG\7\4\2\2G\7\3\2\2\2HL\5\n\6\2IL\5\f\7\2JL\5\16\b\2K"+
		"H\3\2\2\2KI\3\2\2\2KJ\3\2\2\2L\t\3\2\2\2MN\7\5\2\2NS\7%\2\2OP\7\6\2\2"+
		"PQ\5\20\t\2QR\7\7\2\2RT\3\2\2\2SO\3\2\2\2ST\3\2\2\2Tl\3\2\2\2UV\7\b\2"+
		"\2V[\7%\2\2WX\7\6\2\2XY\5\20\t\2YZ\7\7\2\2Z\\\3\2\2\2[W\3\2\2\2[\\\3\2"+
		"\2\2\\^\3\2\2\2]_\5\6\4\2^]\3\2\2\2^_\3\2\2\2_l\3\2\2\2`a\7%\2\2af\7%"+
		"\2\2bc\7\6\2\2cd\5\20\t\2de\7\7\2\2eg\3\2\2\2fb\3\2\2\2fg\3\2\2\2gi\3"+
		"\2\2\2hj\5\6\4\2ih\3\2\2\2ij\3\2\2\2jl\3\2\2\2kM\3\2\2\2kU\3\2\2\2k`\3"+
		"\2\2\2l\13\3\2\2\2mn\t\2\2\2no\7%\2\2op\7\r\2\2pu\7%\2\2qr\7\6\2\2rs\5"+
		"\20\t\2st\7\7\2\2tv\3\2\2\2uq\3\2\2\2uv\3\2\2\2v\r\3\2\2\2wz\7\16\2\2"+
		"x{\5\30\r\2y{\5\6\4\2zx\3\2\2\2zy\3\2\2\2{\17\3\2\2\2|\u0081\5\22\n\2"+
		"}~\7\17\2\2~\u0080\5\20\t\2\177}\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177"+
		"\3\2\2\2\u0081\u0082\3\2\2\2\u0082\21\3\2\2\2\u0083\u0081\3\2\2\2\u0084"+
		"\u0085\5\26\f\2\u0085\23\3\2\2\2\u0086\u0089\5\26\f\2\u0087\u0089\5\30"+
		"\r\2\u0088\u0086\3\2\2\2\u0088\u0087\3\2\2\2\u0089\25\3\2\2\2\u008a\u008b"+
		"\7%\2\2\u008b\u008c\7\20\2\2\u008c\u008d\5\30\r\2\u008d\u008e\b\f\1\2"+
		"\u008e\u009a\3\2\2\2\u008f\u0090\7%\2\2\u0090\u0091\7\21\2\2\u0091\u0092"+
		"\5\30\r\2\u0092\u0093\b\f\1\2\u0093\u009a\3\2\2\2\u0094\u0095\5 \21\2"+
		"\u0095\u0096\7\20\2\2\u0096\u0097\5\30\r\2\u0097\u0098\b\f\1\2\u0098\u009a"+
		"\3\2\2\2\u0099\u008a\3\2\2\2\u0099\u008f\3\2\2\2\u0099\u0094\3\2\2\2\u009a"+
		"\27\3\2\2\2\u009b\u009c\b\r\1\2\u009c\u009d\t\3\2\2\u009d\u009e\5\30\r"+
		"\20\u009e\u009f\b\r\1\2\u009f\u00be\3\2\2\2\u00a0\u00a1\7!\2\2\u00a1\u00a2"+
		"\7\6\2\2\u00a2\u00a3\5\30\r\2\u00a3\u00a4\7\17\2\2\u00a4\u00a5\5\30\r"+
		"\2\u00a5\u00a6\7\17\2\2\u00a6\u00a7\5\30\r\2\u00a7\u00a8\7\7\2\2\u00a8"+
		"\u00a9\b\r\1\2\u00a9\u00be\3\2\2\2\u00aa\u00ab\5\32\16\2\u00ab\u00ac\b"+
		"\r\1\2\u00ac\u00be\3\2\2\2\u00ad\u00ae\5 \21\2\u00ae\u00af\b\r\1\2\u00af"+
		"\u00be\3\2\2\2\u00b0\u00b1\5\"\22\2\u00b1\u00b2\b\r\1\2\u00b2\u00be\3"+
		"\2\2\2\u00b3\u00b4\5$\23\2\u00b4\u00b5\b\r\1\2\u00b5\u00be\3\2\2\2\u00b6"+
		"\u00b7\7%\2\2\u00b7\u00be\b\r\1\2\u00b8\u00b9\7\6\2\2\u00b9\u00ba\5\30"+
		"\r\2\u00ba\u00bb\7\7\2\2\u00bb\u00bc\b\r\1\2\u00bc\u00be\3\2\2\2\u00bd"+
		"\u009b\3\2\2\2\u00bd\u00a0\3\2\2\2\u00bd\u00aa\3\2\2\2\u00bd\u00ad\3\2"+
		"\2\2\u00bd\u00b0\3\2\2\2\u00bd\u00b3\3\2\2\2\u00bd\u00b6\3\2\2\2\u00bd"+
		"\u00b8\3\2\2\2\u00be\u00df\3\2\2\2\u00bf\u00c0\f\17\2\2\u00c0\u00c1\t"+
		"\4\2\2\u00c1\u00c2\5\30\r\20\u00c2\u00c3\b\r\1\2\u00c3\u00de\3\2\2\2\u00c4"+
		"\u00c5\f\16\2\2\u00c5\u00c6\t\5\2\2\u00c6\u00c7\5\30\r\17\u00c7\u00c8"+
		"\b\r\1\2\u00c8\u00de\3\2\2\2\u00c9\u00ca\f\r\2\2\u00ca\u00cb\t\6\2\2\u00cb"+
		"\u00cc\5\30\r\16\u00cc\u00cd\b\r\1\2\u00cd\u00de\3\2\2\2\u00ce\u00cf\f"+
		"\f\2\2\u00cf\u00d0\t\7\2\2\u00d0\u00d1\5\30\r\r\u00d1\u00d2\b\r\1\2\u00d2"+
		"\u00de\3\2\2\2\u00d3\u00d4\f\13\2\2\u00d4\u00d5\7\37\2\2\u00d5\u00d6\5"+
		"\30\r\f\u00d6\u00d7\b\r\1\2\u00d7\u00de\3\2\2\2\u00d8\u00d9\f\n\2\2\u00d9"+
		"\u00da\7 \2\2\u00da\u00db\5\30\r\13\u00db\u00dc\b\r\1\2\u00dc\u00de\3"+
		"\2\2\2\u00dd\u00bf\3\2\2\2\u00dd\u00c4\3\2\2\2\u00dd\u00c9\3\2\2\2\u00dd"+
		"\u00ce\3\2\2\2\u00dd\u00d3\3\2\2\2\u00dd\u00d8\3\2\2\2\u00de\u00e1\3\2"+
		"\2\2\u00df\u00dd\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\31\3\2\2\2\u00e1\u00df"+
		"\3\2\2\2\u00e2\u00e3\7%\2\2\u00e3\u00e4\7\6\2\2\u00e4\u00e5\5\34\17\2"+
		"\u00e5\u00e6\7\7\2\2\u00e6\33\3\2\2\2\u00e7\u00ec\5\36\20\2\u00e8\u00e9"+
		"\7\17\2\2\u00e9\u00eb\5\34\17\2\u00ea\u00e8\3\2\2\2\u00eb\u00ee\3\2\2"+
		"\2\u00ec\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\35\3\2\2\2\u00ee\u00ec"+
		"\3\2\2\2\u00ef\u00f0\5\30\r\2\u00f0\37\3\2\2\2\u00f1\u00f2\7\"\2\2\u00f2"+
		"\u00f3\7%\2\2\u00f3!\3\2\2\2\u00f4\u00f5\7#\2\2\u00f5\u00f6\7%\2\2\u00f6"+
		"#\3\2\2\2\u00f7\u00f8\7&\2\2\u00f8\u0100\b\23\1\2\u00f9\u00fa\7\'\2\2"+
		"\u00fa\u0100\b\23\1\2\u00fb\u00fc\7$\2\2\u00fc\u0100\b\23\1\2\u00fd\u00fe"+
		"\7(\2\2\u00fe\u0100\b\23\1\2\u00ff\u00f7\3\2\2\2\u00ff\u00f9\3\2\2\2\u00ff"+
		"\u00fb\3\2\2\2\u00ff\u00fd\3\2\2\2\u0100%\3\2\2\2\32\',\61\66;?CKS[^f"+
		"ikuz\u0081\u0088\u0099\u00bd\u00dd\u00df\u00ec\u00ff";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}