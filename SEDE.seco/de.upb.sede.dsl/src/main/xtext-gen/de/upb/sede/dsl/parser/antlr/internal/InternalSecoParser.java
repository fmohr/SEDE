package de.upb.sede.dsl.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import de.upb.sede.dsl.services.SecoGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalSecoParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_INT", "RULE_ID", "RULE_STRING", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'='", "'::'", "'.'", "'('", "'{'", "'}'", "');'", "','", "'i'", "'True'", "'true'", "'False'", "'false'", "'TRUE'", "'FALSE'", "'Null'", "'null'", "'NULL'", "'NuLL'", "'-'", "'+'", "'e'", "'E'"
    };
    public static final int RULE_STRING=6;
    public static final int RULE_SL_COMMENT=8;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__11=11;
    public static final int T__33=33;
    public static final int T__12=12;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int EOF=-1;
    public static final int T__30=30;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int RULE_ID=5;
    public static final int RULE_WS=9;
    public static final int RULE_ANY_OTHER=10;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int RULE_INT=4;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int RULE_ML_COMMENT=7;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int T__20=20;
    public static final int T__21=21;

    // delegates
    // delegators


        public InternalSecoParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalSecoParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalSecoParser.tokenNames; }
    public String getGrammarFileName() { return "InternalSeco.g"; }



     	private SecoGrammarAccess grammarAccess;

        public InternalSecoParser(TokenStream input, SecoGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Entries";
       	}

       	@Override
       	protected SecoGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleEntries"
    // InternalSeco.g:64:1: entryRuleEntries returns [EObject current=null] : iv_ruleEntries= ruleEntries EOF ;
    public final EObject entryRuleEntries() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEntries = null;


        try {
            // InternalSeco.g:64:48: (iv_ruleEntries= ruleEntries EOF )
            // InternalSeco.g:65:2: iv_ruleEntries= ruleEntries EOF
            {
             newCompositeNode(grammarAccess.getEntriesRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEntries=ruleEntries();

            state._fsp--;

             current =iv_ruleEntries; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEntries"


    // $ANTLR start "ruleEntries"
    // InternalSeco.g:71:1: ruleEntries returns [EObject current=null] : ( (lv_elements_0_0= ruleEntry ) )* ;
    public final EObject ruleEntries() throws RecognitionException {
        EObject current = null;

        EObject lv_elements_0_0 = null;



        	enterRule();

        try {
            // InternalSeco.g:77:2: ( ( (lv_elements_0_0= ruleEntry ) )* )
            // InternalSeco.g:78:2: ( (lv_elements_0_0= ruleEntry ) )*
            {
            // InternalSeco.g:78:2: ( (lv_elements_0_0= ruleEntry ) )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==RULE_ID) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalSeco.g:79:3: (lv_elements_0_0= ruleEntry )
            	    {
            	    // InternalSeco.g:79:3: (lv_elements_0_0= ruleEntry )
            	    // InternalSeco.g:80:4: lv_elements_0_0= ruleEntry
            	    {

            	    				newCompositeNode(grammarAccess.getEntriesAccess().getElementsEntryParserRuleCall_0());
            	    			
            	    pushFollow(FOLLOW_3);
            	    lv_elements_0_0=ruleEntry();

            	    state._fsp--;


            	    				if (current==null) {
            	    					current = createModelElementForParent(grammarAccess.getEntriesRule());
            	    				}
            	    				add(
            	    					current,
            	    					"elements",
            	    					lv_elements_0_0,
            	    					"de.upb.sede.dsl.Seco.Entry");
            	    				afterParserOrEnumRuleCall();
            	    			

            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEntries"


    // $ANTLR start "entryRuleEntry"
    // InternalSeco.g:100:1: entryRuleEntry returns [EObject current=null] : iv_ruleEntry= ruleEntry EOF ;
    public final EObject entryRuleEntry() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEntry = null;


        try {
            // InternalSeco.g:100:46: (iv_ruleEntry= ruleEntry EOF )
            // InternalSeco.g:101:2: iv_ruleEntry= ruleEntry EOF
            {
             newCompositeNode(grammarAccess.getEntryRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEntry=ruleEntry();

            state._fsp--;

             current =iv_ruleEntry; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEntry"


    // $ANTLR start "ruleEntry"
    // InternalSeco.g:107:1: ruleEntry returns [EObject current=null] : ( (lv_instruction_0_0= ruleInstruction ) ) ;
    public final EObject ruleEntry() throws RecognitionException {
        EObject current = null;

        EObject lv_instruction_0_0 = null;



        	enterRule();

        try {
            // InternalSeco.g:113:2: ( ( (lv_instruction_0_0= ruleInstruction ) ) )
            // InternalSeco.g:114:2: ( (lv_instruction_0_0= ruleInstruction ) )
            {
            // InternalSeco.g:114:2: ( (lv_instruction_0_0= ruleInstruction ) )
            // InternalSeco.g:115:3: (lv_instruction_0_0= ruleInstruction )
            {
            // InternalSeco.g:115:3: (lv_instruction_0_0= ruleInstruction )
            // InternalSeco.g:116:4: lv_instruction_0_0= ruleInstruction
            {

            				newCompositeNode(grammarAccess.getEntryAccess().getInstructionInstructionParserRuleCall_0());
            			
            pushFollow(FOLLOW_2);
            lv_instruction_0_0=ruleInstruction();

            state._fsp--;


            				if (current==null) {
            					current = createModelElementForParent(grammarAccess.getEntryRule());
            				}
            				set(
            					current,
            					"instruction",
            					lv_instruction_0_0,
            					"de.upb.sede.dsl.Seco.Instruction");
            				afterParserOrEnumRuleCall();
            			

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEntry"


    // $ANTLR start "entryRuleInstruction"
    // InternalSeco.g:136:1: entryRuleInstruction returns [EObject current=null] : iv_ruleInstruction= ruleInstruction EOF ;
    public final EObject entryRuleInstruction() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInstruction = null;


        try {
            // InternalSeco.g:136:52: (iv_ruleInstruction= ruleInstruction EOF )
            // InternalSeco.g:137:2: iv_ruleInstruction= ruleInstruction EOF
            {
             newCompositeNode(grammarAccess.getInstructionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInstruction=ruleInstruction();

            state._fsp--;

             current =iv_ruleInstruction; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInstruction"


    // $ANTLR start "ruleInstruction"
    // InternalSeco.g:143:1: ruleInstruction returns [EObject current=null] : ( ( ( (lv_field_0_0= ruleFieldName ) ) otherlv_1= '=' )? ( (lv_context_2_0= ruleQualifiedName ) ) (otherlv_3= '::' | otherlv_4= '.' ) ( (lv_method_5_0= ruleMethodName ) ) otherlv_6= '(' (otherlv_7= '{' )? ( (lv_inputs_8_0= ruleParameters ) )? (otherlv_9= '}' )? otherlv_10= ');' ) ;
    public final EObject ruleInstruction() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        AntlrDatatypeRuleToken lv_field_0_0 = null;

        AntlrDatatypeRuleToken lv_context_2_0 = null;

        AntlrDatatypeRuleToken lv_method_5_0 = null;

        EObject lv_inputs_8_0 = null;



        	enterRule();

        try {
            // InternalSeco.g:149:2: ( ( ( ( (lv_field_0_0= ruleFieldName ) ) otherlv_1= '=' )? ( (lv_context_2_0= ruleQualifiedName ) ) (otherlv_3= '::' | otherlv_4= '.' ) ( (lv_method_5_0= ruleMethodName ) ) otherlv_6= '(' (otherlv_7= '{' )? ( (lv_inputs_8_0= ruleParameters ) )? (otherlv_9= '}' )? otherlv_10= ');' ) )
            // InternalSeco.g:150:2: ( ( ( (lv_field_0_0= ruleFieldName ) ) otherlv_1= '=' )? ( (lv_context_2_0= ruleQualifiedName ) ) (otherlv_3= '::' | otherlv_4= '.' ) ( (lv_method_5_0= ruleMethodName ) ) otherlv_6= '(' (otherlv_7= '{' )? ( (lv_inputs_8_0= ruleParameters ) )? (otherlv_9= '}' )? otherlv_10= ');' )
            {
            // InternalSeco.g:150:2: ( ( ( (lv_field_0_0= ruleFieldName ) ) otherlv_1= '=' )? ( (lv_context_2_0= ruleQualifiedName ) ) (otherlv_3= '::' | otherlv_4= '.' ) ( (lv_method_5_0= ruleMethodName ) ) otherlv_6= '(' (otherlv_7= '{' )? ( (lv_inputs_8_0= ruleParameters ) )? (otherlv_9= '}' )? otherlv_10= ');' )
            // InternalSeco.g:151:3: ( ( (lv_field_0_0= ruleFieldName ) ) otherlv_1= '=' )? ( (lv_context_2_0= ruleQualifiedName ) ) (otherlv_3= '::' | otherlv_4= '.' ) ( (lv_method_5_0= ruleMethodName ) ) otherlv_6= '(' (otherlv_7= '{' )? ( (lv_inputs_8_0= ruleParameters ) )? (otherlv_9= '}' )? otherlv_10= ');'
            {
            // InternalSeco.g:151:3: ( ( (lv_field_0_0= ruleFieldName ) ) otherlv_1= '=' )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==RULE_ID) ) {
                int LA2_1 = input.LA(2);

                if ( (LA2_1==11) ) {
                    alt2=1;
                }
            }
            switch (alt2) {
                case 1 :
                    // InternalSeco.g:152:4: ( (lv_field_0_0= ruleFieldName ) ) otherlv_1= '='
                    {
                    // InternalSeco.g:152:4: ( (lv_field_0_0= ruleFieldName ) )
                    // InternalSeco.g:153:5: (lv_field_0_0= ruleFieldName )
                    {
                    // InternalSeco.g:153:5: (lv_field_0_0= ruleFieldName )
                    // InternalSeco.g:154:6: lv_field_0_0= ruleFieldName
                    {

                    						newCompositeNode(grammarAccess.getInstructionAccess().getFieldFieldNameParserRuleCall_0_0_0());
                    					
                    pushFollow(FOLLOW_4);
                    lv_field_0_0=ruleFieldName();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getInstructionRule());
                    						}
                    						set(
                    							current,
                    							"field",
                    							lv_field_0_0,
                    							"de.upb.sede.dsl.Seco.FieldName");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    otherlv_1=(Token)match(input,11,FOLLOW_5); 

                    				newLeafNode(otherlv_1, grammarAccess.getInstructionAccess().getEqualsSignKeyword_0_1());
                    			

                    }
                    break;

            }

            // InternalSeco.g:176:3: ( (lv_context_2_0= ruleQualifiedName ) )
            // InternalSeco.g:177:4: (lv_context_2_0= ruleQualifiedName )
            {
            // InternalSeco.g:177:4: (lv_context_2_0= ruleQualifiedName )
            // InternalSeco.g:178:5: lv_context_2_0= ruleQualifiedName
            {

            					newCompositeNode(grammarAccess.getInstructionAccess().getContextQualifiedNameParserRuleCall_1_0());
            				
            pushFollow(FOLLOW_6);
            lv_context_2_0=ruleQualifiedName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInstructionRule());
            					}
            					set(
            						current,
            						"context",
            						lv_context_2_0,
            						"de.upb.sede.dsl.Seco.QualifiedName");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSeco.g:195:3: (otherlv_3= '::' | otherlv_4= '.' )
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==12) ) {
                alt3=1;
            }
            else if ( (LA3_0==13) ) {
                alt3=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }
            switch (alt3) {
                case 1 :
                    // InternalSeco.g:196:4: otherlv_3= '::'
                    {
                    otherlv_3=(Token)match(input,12,FOLLOW_7); 

                    				newLeafNode(otherlv_3, grammarAccess.getInstructionAccess().getColonColonKeyword_2_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalSeco.g:201:4: otherlv_4= '.'
                    {
                    otherlv_4=(Token)match(input,13,FOLLOW_7); 

                    				newLeafNode(otherlv_4, grammarAccess.getInstructionAccess().getFullStopKeyword_2_1());
                    			

                    }
                    break;

            }

            // InternalSeco.g:206:3: ( (lv_method_5_0= ruleMethodName ) )
            // InternalSeco.g:207:4: (lv_method_5_0= ruleMethodName )
            {
            // InternalSeco.g:207:4: (lv_method_5_0= ruleMethodName )
            // InternalSeco.g:208:5: lv_method_5_0= ruleMethodName
            {

            					newCompositeNode(grammarAccess.getInstructionAccess().getMethodMethodNameParserRuleCall_3_0());
            				
            pushFollow(FOLLOW_8);
            lv_method_5_0=ruleMethodName();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInstructionRule());
            					}
            					set(
            						current,
            						"method",
            						lv_method_5_0,
            						"de.upb.sede.dsl.Seco.MethodName");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_6=(Token)match(input,14,FOLLOW_9); 

            			newLeafNode(otherlv_6, grammarAccess.getInstructionAccess().getLeftParenthesisKeyword_4());
            		
            // InternalSeco.g:229:3: (otherlv_7= '{' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==15) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalSeco.g:230:4: otherlv_7= '{'
                    {
                    otherlv_7=(Token)match(input,15,FOLLOW_10); 

                    				newLeafNode(otherlv_7, grammarAccess.getInstructionAccess().getLeftCurlyBracketKeyword_5());
                    			

                    }
                    break;

            }

            // InternalSeco.g:235:3: ( (lv_inputs_8_0= ruleParameters ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( ((LA5_0>=RULE_INT && LA5_0<=RULE_STRING)||(LA5_0>=19 && LA5_0<=31)) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalSeco.g:236:4: (lv_inputs_8_0= ruleParameters )
                    {
                    // InternalSeco.g:236:4: (lv_inputs_8_0= ruleParameters )
                    // InternalSeco.g:237:5: lv_inputs_8_0= ruleParameters
                    {

                    					newCompositeNode(grammarAccess.getInstructionAccess().getInputsParametersParserRuleCall_6_0());
                    				
                    pushFollow(FOLLOW_11);
                    lv_inputs_8_0=ruleParameters();

                    state._fsp--;


                    					if (current==null) {
                    						current = createModelElementForParent(grammarAccess.getInstructionRule());
                    					}
                    					set(
                    						current,
                    						"inputs",
                    						lv_inputs_8_0,
                    						"de.upb.sede.dsl.Seco.Parameters");
                    					afterParserOrEnumRuleCall();
                    				

                    }


                    }
                    break;

            }

            // InternalSeco.g:254:3: (otherlv_9= '}' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==16) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalSeco.g:255:4: otherlv_9= '}'
                    {
                    otherlv_9=(Token)match(input,16,FOLLOW_12); 

                    				newLeafNode(otherlv_9, grammarAccess.getInstructionAccess().getRightCurlyBracketKeyword_7());
                    			

                    }
                    break;

            }

            otherlv_10=(Token)match(input,17,FOLLOW_2); 

            			newLeafNode(otherlv_10, grammarAccess.getInstructionAccess().getRightParenthesisSemicolonKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInstruction"


    // $ANTLR start "entryRuleParameters"
    // InternalSeco.g:268:1: entryRuleParameters returns [EObject current=null] : iv_ruleParameters= ruleParameters EOF ;
    public final EObject entryRuleParameters() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameters = null;


        try {
            // InternalSeco.g:268:51: (iv_ruleParameters= ruleParameters EOF )
            // InternalSeco.g:269:2: iv_ruleParameters= ruleParameters EOF
            {
             newCompositeNode(grammarAccess.getParametersRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParameters=ruleParameters();

            state._fsp--;

             current =iv_ruleParameters; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameters"


    // $ANTLR start "ruleParameters"
    // InternalSeco.g:275:1: ruleParameters returns [EObject current=null] : ( ( (lv_inputs_0_0= ruleParameter ) ) (otherlv_1= ',' ( (lv_inputs_2_0= ruleParameter ) ) )* ) ;
    public final EObject ruleParameters() throws RecognitionException {
        EObject current = null;

        Token otherlv_1=null;
        EObject lv_inputs_0_0 = null;

        EObject lv_inputs_2_0 = null;



        	enterRule();

        try {
            // InternalSeco.g:281:2: ( ( ( (lv_inputs_0_0= ruleParameter ) ) (otherlv_1= ',' ( (lv_inputs_2_0= ruleParameter ) ) )* ) )
            // InternalSeco.g:282:2: ( ( (lv_inputs_0_0= ruleParameter ) ) (otherlv_1= ',' ( (lv_inputs_2_0= ruleParameter ) ) )* )
            {
            // InternalSeco.g:282:2: ( ( (lv_inputs_0_0= ruleParameter ) ) (otherlv_1= ',' ( (lv_inputs_2_0= ruleParameter ) ) )* )
            // InternalSeco.g:283:3: ( (lv_inputs_0_0= ruleParameter ) ) (otherlv_1= ',' ( (lv_inputs_2_0= ruleParameter ) ) )*
            {
            // InternalSeco.g:283:3: ( (lv_inputs_0_0= ruleParameter ) )
            // InternalSeco.g:284:4: (lv_inputs_0_0= ruleParameter )
            {
            // InternalSeco.g:284:4: (lv_inputs_0_0= ruleParameter )
            // InternalSeco.g:285:5: lv_inputs_0_0= ruleParameter
            {

            					newCompositeNode(grammarAccess.getParametersAccess().getInputsParameterParserRuleCall_0_0());
            				
            pushFollow(FOLLOW_13);
            lv_inputs_0_0=ruleParameter();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getParametersRule());
            					}
            					add(
            						current,
            						"inputs",
            						lv_inputs_0_0,
            						"de.upb.sede.dsl.Seco.Parameter");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalSeco.g:302:3: (otherlv_1= ',' ( (lv_inputs_2_0= ruleParameter ) ) )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==18) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // InternalSeco.g:303:4: otherlv_1= ',' ( (lv_inputs_2_0= ruleParameter ) )
            	    {
            	    otherlv_1=(Token)match(input,18,FOLLOW_14); 

            	    				newLeafNode(otherlv_1, grammarAccess.getParametersAccess().getCommaKeyword_1_0());
            	    			
            	    // InternalSeco.g:307:4: ( (lv_inputs_2_0= ruleParameter ) )
            	    // InternalSeco.g:308:5: (lv_inputs_2_0= ruleParameter )
            	    {
            	    // InternalSeco.g:308:5: (lv_inputs_2_0= ruleParameter )
            	    // InternalSeco.g:309:6: lv_inputs_2_0= ruleParameter
            	    {

            	    						newCompositeNode(grammarAccess.getParametersAccess().getInputsParameterParserRuleCall_1_1_0());
            	    					
            	    pushFollow(FOLLOW_13);
            	    lv_inputs_2_0=ruleParameter();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getParametersRule());
            	    						}
            	    						add(
            	    							current,
            	    							"inputs",
            	    							lv_inputs_2_0,
            	    							"de.upb.sede.dsl.Seco.Parameter");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameters"


    // $ANTLR start "entryRuleParameter"
    // InternalSeco.g:331:1: entryRuleParameter returns [EObject current=null] : iv_ruleParameter= ruleParameter EOF ;
    public final EObject entryRuleParameter() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParameter = null;


        try {
            // InternalSeco.g:331:50: (iv_ruleParameter= ruleParameter EOF )
            // InternalSeco.g:332:2: iv_ruleParameter= ruleParameter EOF
            {
             newCompositeNode(grammarAccess.getParameterRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParameter=ruleParameter();

            state._fsp--;

             current =iv_ruleParameter; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParameter"


    // $ANTLR start "ruleParameter"
    // InternalSeco.g:338:1: ruleParameter returns [EObject current=null] : ( ( ( (lv_isIndexed_0_0= 'i' ) ) ( (lv_index_1_0= RULE_INT ) ) )? ( ( (lv_isNumb_2_0= ruleNumberConst ) ) | ( (lv_isString_3_0= ruleStringConst ) ) | ( (lv_isBool_4_0= ruleBoolConst ) ) | ( (lv_isNull_5_0= ruleNullConst ) ) | ( (lv_isField_6_0= ruleFieldName ) ) ) ) ;
    public final EObject ruleParameter() throws RecognitionException {
        EObject current = null;

        Token lv_isIndexed_0_0=null;
        Token lv_index_1_0=null;
        AntlrDatatypeRuleToken lv_isNumb_2_0 = null;

        AntlrDatatypeRuleToken lv_isString_3_0 = null;

        AntlrDatatypeRuleToken lv_isBool_4_0 = null;

        AntlrDatatypeRuleToken lv_isNull_5_0 = null;

        AntlrDatatypeRuleToken lv_isField_6_0 = null;



        	enterRule();

        try {
            // InternalSeco.g:344:2: ( ( ( ( (lv_isIndexed_0_0= 'i' ) ) ( (lv_index_1_0= RULE_INT ) ) )? ( ( (lv_isNumb_2_0= ruleNumberConst ) ) | ( (lv_isString_3_0= ruleStringConst ) ) | ( (lv_isBool_4_0= ruleBoolConst ) ) | ( (lv_isNull_5_0= ruleNullConst ) ) | ( (lv_isField_6_0= ruleFieldName ) ) ) ) )
            // InternalSeco.g:345:2: ( ( ( (lv_isIndexed_0_0= 'i' ) ) ( (lv_index_1_0= RULE_INT ) ) )? ( ( (lv_isNumb_2_0= ruleNumberConst ) ) | ( (lv_isString_3_0= ruleStringConst ) ) | ( (lv_isBool_4_0= ruleBoolConst ) ) | ( (lv_isNull_5_0= ruleNullConst ) ) | ( (lv_isField_6_0= ruleFieldName ) ) ) )
            {
            // InternalSeco.g:345:2: ( ( ( (lv_isIndexed_0_0= 'i' ) ) ( (lv_index_1_0= RULE_INT ) ) )? ( ( (lv_isNumb_2_0= ruleNumberConst ) ) | ( (lv_isString_3_0= ruleStringConst ) ) | ( (lv_isBool_4_0= ruleBoolConst ) ) | ( (lv_isNull_5_0= ruleNullConst ) ) | ( (lv_isField_6_0= ruleFieldName ) ) ) )
            // InternalSeco.g:346:3: ( ( (lv_isIndexed_0_0= 'i' ) ) ( (lv_index_1_0= RULE_INT ) ) )? ( ( (lv_isNumb_2_0= ruleNumberConst ) ) | ( (lv_isString_3_0= ruleStringConst ) ) | ( (lv_isBool_4_0= ruleBoolConst ) ) | ( (lv_isNull_5_0= ruleNullConst ) ) | ( (lv_isField_6_0= ruleFieldName ) ) )
            {
            // InternalSeco.g:346:3: ( ( (lv_isIndexed_0_0= 'i' ) ) ( (lv_index_1_0= RULE_INT ) ) )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==19) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // InternalSeco.g:347:4: ( (lv_isIndexed_0_0= 'i' ) ) ( (lv_index_1_0= RULE_INT ) )
                    {
                    // InternalSeco.g:347:4: ( (lv_isIndexed_0_0= 'i' ) )
                    // InternalSeco.g:348:5: (lv_isIndexed_0_0= 'i' )
                    {
                    // InternalSeco.g:348:5: (lv_isIndexed_0_0= 'i' )
                    // InternalSeco.g:349:6: lv_isIndexed_0_0= 'i'
                    {
                    lv_isIndexed_0_0=(Token)match(input,19,FOLLOW_15); 

                    						newLeafNode(lv_isIndexed_0_0, grammarAccess.getParameterAccess().getIsIndexedIKeyword_0_0_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getParameterRule());
                    						}
                    						setWithLastConsumed(current, "isIndexed", true, "i");
                    					

                    }


                    }

                    // InternalSeco.g:361:4: ( (lv_index_1_0= RULE_INT ) )
                    // InternalSeco.g:362:5: (lv_index_1_0= RULE_INT )
                    {
                    // InternalSeco.g:362:5: (lv_index_1_0= RULE_INT )
                    // InternalSeco.g:363:6: lv_index_1_0= RULE_INT
                    {
                    lv_index_1_0=(Token)match(input,RULE_INT,FOLLOW_16); 

                    						newLeafNode(lv_index_1_0, grammarAccess.getParameterAccess().getIndexINTTerminalRuleCall_0_1_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getParameterRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"index",
                    							lv_index_1_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalSeco.g:380:3: ( ( (lv_isNumb_2_0= ruleNumberConst ) ) | ( (lv_isString_3_0= ruleStringConst ) ) | ( (lv_isBool_4_0= ruleBoolConst ) ) | ( (lv_isNull_5_0= ruleNullConst ) ) | ( (lv_isField_6_0= ruleFieldName ) ) )
            int alt9=5;
            switch ( input.LA(1) ) {
            case RULE_INT:
            case 30:
            case 31:
                {
                alt9=1;
                }
                break;
            case RULE_STRING:
                {
                alt9=2;
                }
                break;
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                {
                alt9=3;
                }
                break;
            case 26:
            case 27:
            case 28:
            case 29:
                {
                alt9=4;
                }
                break;
            case RULE_ID:
                {
                alt9=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // InternalSeco.g:381:4: ( (lv_isNumb_2_0= ruleNumberConst ) )
                    {
                    // InternalSeco.g:381:4: ( (lv_isNumb_2_0= ruleNumberConst ) )
                    // InternalSeco.g:382:5: (lv_isNumb_2_0= ruleNumberConst )
                    {
                    // InternalSeco.g:382:5: (lv_isNumb_2_0= ruleNumberConst )
                    // InternalSeco.g:383:6: lv_isNumb_2_0= ruleNumberConst
                    {

                    						newCompositeNode(grammarAccess.getParameterAccess().getIsNumbNumberConstParserRuleCall_1_0_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_isNumb_2_0=ruleNumberConst();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getParameterRule());
                    						}
                    						set(
                    							current,
                    							"isNumb",
                    							true,
                    							"de.upb.sede.dsl.Seco.NumberConst");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 2 :
                    // InternalSeco.g:401:4: ( (lv_isString_3_0= ruleStringConst ) )
                    {
                    // InternalSeco.g:401:4: ( (lv_isString_3_0= ruleStringConst ) )
                    // InternalSeco.g:402:5: (lv_isString_3_0= ruleStringConst )
                    {
                    // InternalSeco.g:402:5: (lv_isString_3_0= ruleStringConst )
                    // InternalSeco.g:403:6: lv_isString_3_0= ruleStringConst
                    {

                    						newCompositeNode(grammarAccess.getParameterAccess().getIsStringStringConstParserRuleCall_1_1_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_isString_3_0=ruleStringConst();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getParameterRule());
                    						}
                    						set(
                    							current,
                    							"isString",
                    							true,
                    							"de.upb.sede.dsl.Seco.StringConst");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 3 :
                    // InternalSeco.g:421:4: ( (lv_isBool_4_0= ruleBoolConst ) )
                    {
                    // InternalSeco.g:421:4: ( (lv_isBool_4_0= ruleBoolConst ) )
                    // InternalSeco.g:422:5: (lv_isBool_4_0= ruleBoolConst )
                    {
                    // InternalSeco.g:422:5: (lv_isBool_4_0= ruleBoolConst )
                    // InternalSeco.g:423:6: lv_isBool_4_0= ruleBoolConst
                    {

                    						newCompositeNode(grammarAccess.getParameterAccess().getIsBoolBoolConstParserRuleCall_1_2_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_isBool_4_0=ruleBoolConst();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getParameterRule());
                    						}
                    						set(
                    							current,
                    							"isBool",
                    							true,
                    							"de.upb.sede.dsl.Seco.BoolConst");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 4 :
                    // InternalSeco.g:441:4: ( (lv_isNull_5_0= ruleNullConst ) )
                    {
                    // InternalSeco.g:441:4: ( (lv_isNull_5_0= ruleNullConst ) )
                    // InternalSeco.g:442:5: (lv_isNull_5_0= ruleNullConst )
                    {
                    // InternalSeco.g:442:5: (lv_isNull_5_0= ruleNullConst )
                    // InternalSeco.g:443:6: lv_isNull_5_0= ruleNullConst
                    {

                    						newCompositeNode(grammarAccess.getParameterAccess().getIsNullNullConstParserRuleCall_1_3_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_isNull_5_0=ruleNullConst();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getParameterRule());
                    						}
                    						set(
                    							current,
                    							"isNull",
                    							true,
                    							"de.upb.sede.dsl.Seco.NullConst");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;
                case 5 :
                    // InternalSeco.g:461:4: ( (lv_isField_6_0= ruleFieldName ) )
                    {
                    // InternalSeco.g:461:4: ( (lv_isField_6_0= ruleFieldName ) )
                    // InternalSeco.g:462:5: (lv_isField_6_0= ruleFieldName )
                    {
                    // InternalSeco.g:462:5: (lv_isField_6_0= ruleFieldName )
                    // InternalSeco.g:463:6: lv_isField_6_0= ruleFieldName
                    {

                    						newCompositeNode(grammarAccess.getParameterAccess().getIsFieldFieldNameParserRuleCall_1_4_0());
                    					
                    pushFollow(FOLLOW_2);
                    lv_isField_6_0=ruleFieldName();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getParameterRule());
                    						}
                    						set(
                    							current,
                    							"isField",
                    							true,
                    							"de.upb.sede.dsl.Seco.FieldName");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParameter"


    // $ANTLR start "entryRuleFieldName"
    // InternalSeco.g:485:1: entryRuleFieldName returns [String current=null] : iv_ruleFieldName= ruleFieldName EOF ;
    public final String entryRuleFieldName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleFieldName = null;


        try {
            // InternalSeco.g:485:49: (iv_ruleFieldName= ruleFieldName EOF )
            // InternalSeco.g:486:2: iv_ruleFieldName= ruleFieldName EOF
            {
             newCompositeNode(grammarAccess.getFieldNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFieldName=ruleFieldName();

            state._fsp--;

             current =iv_ruleFieldName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFieldName"


    // $ANTLR start "ruleFieldName"
    // InternalSeco.g:492:1: ruleFieldName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleFieldName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;


        	enterRule();

        try {
            // InternalSeco.g:498:2: (this_ID_0= RULE_ID )
            // InternalSeco.g:499:2: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            		current.merge(this_ID_0);
            	

            		newLeafNode(this_ID_0, grammarAccess.getFieldNameAccess().getIDTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFieldName"


    // $ANTLR start "entryRuleMethodName"
    // InternalSeco.g:509:1: entryRuleMethodName returns [String current=null] : iv_ruleMethodName= ruleMethodName EOF ;
    public final String entryRuleMethodName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleMethodName = null;


        try {
            // InternalSeco.g:509:50: (iv_ruleMethodName= ruleMethodName EOF )
            // InternalSeco.g:510:2: iv_ruleMethodName= ruleMethodName EOF
            {
             newCompositeNode(grammarAccess.getMethodNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMethodName=ruleMethodName();

            state._fsp--;

             current =iv_ruleMethodName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMethodName"


    // $ANTLR start "ruleMethodName"
    // InternalSeco.g:516:1: ruleMethodName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_ID_0= RULE_ID ;
    public final AntlrDatatypeRuleToken ruleMethodName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;


        	enterRule();

        try {
            // InternalSeco.g:522:2: (this_ID_0= RULE_ID )
            // InternalSeco.g:523:2: this_ID_0= RULE_ID
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_2); 

            		current.merge(this_ID_0);
            	

            		newLeafNode(this_ID_0, grammarAccess.getMethodNameAccess().getIDTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMethodName"


    // $ANTLR start "entryRuleQualifiedName"
    // InternalSeco.g:533:1: entryRuleQualifiedName returns [String current=null] : iv_ruleQualifiedName= ruleQualifiedName EOF ;
    public final String entryRuleQualifiedName() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleQualifiedName = null;


        try {
            // InternalSeco.g:533:53: (iv_ruleQualifiedName= ruleQualifiedName EOF )
            // InternalSeco.g:534:2: iv_ruleQualifiedName= ruleQualifiedName EOF
            {
             newCompositeNode(grammarAccess.getQualifiedNameRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleQualifiedName=ruleQualifiedName();

            state._fsp--;

             current =iv_ruleQualifiedName.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleQualifiedName"


    // $ANTLR start "ruleQualifiedName"
    // InternalSeco.g:540:1: ruleQualifiedName returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) ;
    public final AntlrDatatypeRuleToken ruleQualifiedName() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_ID_0=null;
        Token kw=null;
        Token this_ID_2=null;


        	enterRule();

        try {
            // InternalSeco.g:546:2: ( (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* ) )
            // InternalSeco.g:547:2: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            {
            // InternalSeco.g:547:2: (this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )* )
            // InternalSeco.g:548:3: this_ID_0= RULE_ID (kw= '.' this_ID_2= RULE_ID )*
            {
            this_ID_0=(Token)match(input,RULE_ID,FOLLOW_17); 

            			current.merge(this_ID_0);
            		

            			newLeafNode(this_ID_0, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0());
            		
            // InternalSeco.g:555:3: (kw= '.' this_ID_2= RULE_ID )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==13) ) {
                    int LA10_2 = input.LA(2);

                    if ( (LA10_2==RULE_ID) ) {
                        int LA10_3 = input.LA(3);

                        if ( (LA10_3==EOF||(LA10_3>=12 && LA10_3<=13)) ) {
                            alt10=1;
                        }


                    }


                }


                switch (alt10) {
            	case 1 :
            	    // InternalSeco.g:556:4: kw= '.' this_ID_2= RULE_ID
            	    {
            	    kw=(Token)match(input,13,FOLLOW_7); 

            	    				current.merge(kw);
            	    				newLeafNode(kw, grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0());
            	    			
            	    this_ID_2=(Token)match(input,RULE_ID,FOLLOW_17); 

            	    				current.merge(this_ID_2);
            	    			

            	    				newLeafNode(this_ID_2, grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1());
            	    			

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleQualifiedName"


    // $ANTLR start "entryRuleStringConst"
    // InternalSeco.g:573:1: entryRuleStringConst returns [String current=null] : iv_ruleStringConst= ruleStringConst EOF ;
    public final String entryRuleStringConst() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleStringConst = null;


        try {
            // InternalSeco.g:573:51: (iv_ruleStringConst= ruleStringConst EOF )
            // InternalSeco.g:574:2: iv_ruleStringConst= ruleStringConst EOF
            {
             newCompositeNode(grammarAccess.getStringConstRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStringConst=ruleStringConst();

            state._fsp--;

             current =iv_ruleStringConst.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStringConst"


    // $ANTLR start "ruleStringConst"
    // InternalSeco.g:580:1: ruleStringConst returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : this_STRING_0= RULE_STRING ;
    public final AntlrDatatypeRuleToken ruleStringConst() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token this_STRING_0=null;


        	enterRule();

        try {
            // InternalSeco.g:586:2: (this_STRING_0= RULE_STRING )
            // InternalSeco.g:587:2: this_STRING_0= RULE_STRING
            {
            this_STRING_0=(Token)match(input,RULE_STRING,FOLLOW_2); 

            		current.merge(this_STRING_0);
            	

            		newLeafNode(this_STRING_0, grammarAccess.getStringConstAccess().getSTRINGTerminalRuleCall());
            	

            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStringConst"


    // $ANTLR start "entryRuleBoolConst"
    // InternalSeco.g:597:1: entryRuleBoolConst returns [String current=null] : iv_ruleBoolConst= ruleBoolConst EOF ;
    public final String entryRuleBoolConst() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBoolConst = null;


        try {
            // InternalSeco.g:597:49: (iv_ruleBoolConst= ruleBoolConst EOF )
            // InternalSeco.g:598:2: iv_ruleBoolConst= ruleBoolConst EOF
            {
             newCompositeNode(grammarAccess.getBoolConstRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBoolConst=ruleBoolConst();

            state._fsp--;

             current =iv_ruleBoolConst.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBoolConst"


    // $ANTLR start "ruleBoolConst"
    // InternalSeco.g:604:1: ruleBoolConst returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'True' | kw= 'true' | kw= 'False' | kw= 'false' | kw= 'TRUE' | kw= 'FALSE' ) ;
    public final AntlrDatatypeRuleToken ruleBoolConst() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSeco.g:610:2: ( (kw= 'True' | kw= 'true' | kw= 'False' | kw= 'false' | kw= 'TRUE' | kw= 'FALSE' ) )
            // InternalSeco.g:611:2: (kw= 'True' | kw= 'true' | kw= 'False' | kw= 'false' | kw= 'TRUE' | kw= 'FALSE' )
            {
            // InternalSeco.g:611:2: (kw= 'True' | kw= 'true' | kw= 'False' | kw= 'false' | kw= 'TRUE' | kw= 'FALSE' )
            int alt11=6;
            switch ( input.LA(1) ) {
            case 20:
                {
                alt11=1;
                }
                break;
            case 21:
                {
                alt11=2;
                }
                break;
            case 22:
                {
                alt11=3;
                }
                break;
            case 23:
                {
                alt11=4;
                }
                break;
            case 24:
                {
                alt11=5;
                }
                break;
            case 25:
                {
                alt11=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // InternalSeco.g:612:3: kw= 'True'
                    {
                    kw=(Token)match(input,20,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBoolConstAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalSeco.g:618:3: kw= 'true'
                    {
                    kw=(Token)match(input,21,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBoolConstAccess().getTrueKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSeco.g:624:3: kw= 'False'
                    {
                    kw=(Token)match(input,22,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBoolConstAccess().getFalseKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSeco.g:630:3: kw= 'false'
                    {
                    kw=(Token)match(input,23,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBoolConstAccess().getFalseKeyword_3());
                    		

                    }
                    break;
                case 5 :
                    // InternalSeco.g:636:3: kw= 'TRUE'
                    {
                    kw=(Token)match(input,24,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBoolConstAccess().getTRUEKeyword_4());
                    		

                    }
                    break;
                case 6 :
                    // InternalSeco.g:642:3: kw= 'FALSE'
                    {
                    kw=(Token)match(input,25,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBoolConstAccess().getFALSEKeyword_5());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBoolConst"


    // $ANTLR start "entryRuleNullConst"
    // InternalSeco.g:651:1: entryRuleNullConst returns [String current=null] : iv_ruleNullConst= ruleNullConst EOF ;
    public final String entryRuleNullConst() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNullConst = null;


        try {
            // InternalSeco.g:651:49: (iv_ruleNullConst= ruleNullConst EOF )
            // InternalSeco.g:652:2: iv_ruleNullConst= ruleNullConst EOF
            {
             newCompositeNode(grammarAccess.getNullConstRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNullConst=ruleNullConst();

            state._fsp--;

             current =iv_ruleNullConst.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNullConst"


    // $ANTLR start "ruleNullConst"
    // InternalSeco.g:658:1: ruleNullConst returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'Null' | kw= 'null' | kw= 'NULL' | kw= 'NuLL' ) ;
    public final AntlrDatatypeRuleToken ruleNullConst() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalSeco.g:664:2: ( (kw= 'Null' | kw= 'null' | kw= 'NULL' | kw= 'NuLL' ) )
            // InternalSeco.g:665:2: (kw= 'Null' | kw= 'null' | kw= 'NULL' | kw= 'NuLL' )
            {
            // InternalSeco.g:665:2: (kw= 'Null' | kw= 'null' | kw= 'NULL' | kw= 'NuLL' )
            int alt12=4;
            switch ( input.LA(1) ) {
            case 26:
                {
                alt12=1;
                }
                break;
            case 27:
                {
                alt12=2;
                }
                break;
            case 28:
                {
                alt12=3;
                }
                break;
            case 29:
                {
                alt12=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // InternalSeco.g:666:3: kw= 'Null'
                    {
                    kw=(Token)match(input,26,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNullConstAccess().getNullKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalSeco.g:672:3: kw= 'null'
                    {
                    kw=(Token)match(input,27,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNullConstAccess().getNullKeyword_1());
                    		

                    }
                    break;
                case 3 :
                    // InternalSeco.g:678:3: kw= 'NULL'
                    {
                    kw=(Token)match(input,28,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNullConstAccess().getNULLKeyword_2());
                    		

                    }
                    break;
                case 4 :
                    // InternalSeco.g:684:3: kw= 'NuLL'
                    {
                    kw=(Token)match(input,29,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getNullConstAccess().getNuLLKeyword_3());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNullConst"


    // $ANTLR start "entryRuleNumberConst"
    // InternalSeco.g:693:1: entryRuleNumberConst returns [String current=null] : iv_ruleNumberConst= ruleNumberConst EOF ;
    public final String entryRuleNumberConst() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleNumberConst = null;


        try {
            // InternalSeco.g:693:51: (iv_ruleNumberConst= ruleNumberConst EOF )
            // InternalSeco.g:694:2: iv_ruleNumberConst= ruleNumberConst EOF
            {
             newCompositeNode(grammarAccess.getNumberConstRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNumberConst=ruleNumberConst();

            state._fsp--;

             current =iv_ruleNumberConst.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNumberConst"


    // $ANTLR start "ruleNumberConst"
    // InternalSeco.g:700:1: ruleNumberConst returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '-' | kw= '+' )? this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? ( (kw= 'e' | kw= 'E' ) (kw= '-' | kw= '+' ) this_INT_9= RULE_INT )? ) ;
    public final AntlrDatatypeRuleToken ruleNumberConst() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;
        Token this_INT_4=null;
        Token this_INT_9=null;


        	enterRule();

        try {
            // InternalSeco.g:706:2: ( ( (kw= '-' | kw= '+' )? this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? ( (kw= 'e' | kw= 'E' ) (kw= '-' | kw= '+' ) this_INT_9= RULE_INT )? ) )
            // InternalSeco.g:707:2: ( (kw= '-' | kw= '+' )? this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? ( (kw= 'e' | kw= 'E' ) (kw= '-' | kw= '+' ) this_INT_9= RULE_INT )? )
            {
            // InternalSeco.g:707:2: ( (kw= '-' | kw= '+' )? this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? ( (kw= 'e' | kw= 'E' ) (kw= '-' | kw= '+' ) this_INT_9= RULE_INT )? )
            // InternalSeco.g:708:3: (kw= '-' | kw= '+' )? this_INT_2= RULE_INT (kw= '.' this_INT_4= RULE_INT )? ( (kw= 'e' | kw= 'E' ) (kw= '-' | kw= '+' ) this_INT_9= RULE_INT )?
            {
            // InternalSeco.g:708:3: (kw= '-' | kw= '+' )?
            int alt13=3;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==30) ) {
                alt13=1;
            }
            else if ( (LA13_0==31) ) {
                alt13=2;
            }
            switch (alt13) {
                case 1 :
                    // InternalSeco.g:709:4: kw= '-'
                    {
                    kw=(Token)match(input,30,FOLLOW_15); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getNumberConstAccess().getHyphenMinusKeyword_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalSeco.g:715:4: kw= '+'
                    {
                    kw=(Token)match(input,31,FOLLOW_15); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getNumberConstAccess().getPlusSignKeyword_0_1());
                    			

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_18); 

            			current.merge(this_INT_2);
            		

            			newLeafNode(this_INT_2, grammarAccess.getNumberConstAccess().getINTTerminalRuleCall_1());
            		
            // InternalSeco.g:728:3: (kw= '.' this_INT_4= RULE_INT )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==13) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalSeco.g:729:4: kw= '.' this_INT_4= RULE_INT
                    {
                    kw=(Token)match(input,13,FOLLOW_15); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getNumberConstAccess().getFullStopKeyword_2_0());
                    			
                    this_INT_4=(Token)match(input,RULE_INT,FOLLOW_19); 

                    				current.merge(this_INT_4);
                    			

                    				newLeafNode(this_INT_4, grammarAccess.getNumberConstAccess().getINTTerminalRuleCall_2_1());
                    			

                    }
                    break;

            }

            // InternalSeco.g:742:3: ( (kw= 'e' | kw= 'E' ) (kw= '-' | kw= '+' ) this_INT_9= RULE_INT )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( ((LA17_0>=32 && LA17_0<=33)) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalSeco.g:743:4: (kw= 'e' | kw= 'E' ) (kw= '-' | kw= '+' ) this_INT_9= RULE_INT
                    {
                    // InternalSeco.g:743:4: (kw= 'e' | kw= 'E' )
                    int alt15=2;
                    int LA15_0 = input.LA(1);

                    if ( (LA15_0==32) ) {
                        alt15=1;
                    }
                    else if ( (LA15_0==33) ) {
                        alt15=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 15, 0, input);

                        throw nvae;
                    }
                    switch (alt15) {
                        case 1 :
                            // InternalSeco.g:744:5: kw= 'e'
                            {
                            kw=(Token)match(input,32,FOLLOW_20); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getNumberConstAccess().getEKeyword_3_0_0());
                            				

                            }
                            break;
                        case 2 :
                            // InternalSeco.g:750:5: kw= 'E'
                            {
                            kw=(Token)match(input,33,FOLLOW_20); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getNumberConstAccess().getEKeyword_3_0_1());
                            				

                            }
                            break;

                    }

                    // InternalSeco.g:756:4: (kw= '-' | kw= '+' )
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==30) ) {
                        alt16=1;
                    }
                    else if ( (LA16_0==31) ) {
                        alt16=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 16, 0, input);

                        throw nvae;
                    }
                    switch (alt16) {
                        case 1 :
                            // InternalSeco.g:757:5: kw= '-'
                            {
                            kw=(Token)match(input,30,FOLLOW_15); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getNumberConstAccess().getHyphenMinusKeyword_3_1_0());
                            				

                            }
                            break;
                        case 2 :
                            // InternalSeco.g:763:5: kw= '+'
                            {
                            kw=(Token)match(input,31,FOLLOW_15); 

                            					current.merge(kw);
                            					newLeafNode(kw, grammarAccess.getNumberConstAccess().getPlusSignKeyword_3_1_1());
                            				

                            }
                            break;

                    }

                    this_INT_9=(Token)match(input,RULE_INT,FOLLOW_2); 

                    				current.merge(this_INT_9);
                    			

                    				newLeafNode(this_INT_9, grammarAccess.getNumberConstAccess().getINTTerminalRuleCall_3_2());
                    			

                    }
                    break;

            }


            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNumberConst"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000003020L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x00000000FFFB8070L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x00000000FFFB0070L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000040002L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x00000000FFF80070L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x00000000FFF00070L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000300002002L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000300000002L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x00000000C0000000L});

}