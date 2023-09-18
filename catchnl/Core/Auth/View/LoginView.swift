//
//  LoginView.swift
//  catchnl
//
//  Created by Fatih Toker on 12.09.2023.
//

import SwiftUI

struct LoginView: View {
    @EnvironmentObject var contentViewModel: ContentViewModel
    @StateObject var authViewModel = AuthViewModel()
    @FocusState var keyboardFocused
    
    var body: some View {
        NavigationView {
            switch authViewModel.state {
            case .nothing:
                VStack{
                    Spacer()
                    Image("logo-dark")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 200)
                        .padding(.bottom)
                                
                    Text("Login")
                        .font(.custom(Font.Poppins_SemiBold, size: 16))
                    
                    Text("Welkom op CATCHNL")
                        .font(.custom(Font.Poppins_Regular, size: 14))
                        
                    
                    InputCellView(hint: "Email",systemImageName: "person.circle" , text: $authViewModel.email, state: .text)
                        .padding(.horizontal)
                        .focused($keyboardFocused)
                        .onAppear{
                            DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) {
                                       keyboardFocused = true
                                   }
                        }
                    
                    InputCellView(hint: "Wachtwoord",systemImageName: "lock" , text: $authViewModel.password, state: .password)
                        .padding(.horizontal)
                    
                    NavigationLink {
                        ResetPasswordView()
                    } label: {
                        Text("Wachtwoord vergeten?")
                            .font(.custom(Font.Poppins_SemiBold, size: 14))
                            .padding(.vertical)
                            .foregroundColor(Color("secondry-color"))
                    }

                    
                    
                    
                    Button {
                        authViewModel.state = .loading
                        authViewModel.signIn{ isSuccess in
                            if isSuccess {
                                contentViewModel.checkIfUserExists()
                                authViewModel.state = .nothing
                            } else {
                                authViewModel.state = .nothing
                            }
                        }
                    } label: {
                        Text("Inloggen")
                            .modifier(ButtonModifier())
                            .font(.custom(Font.Poppins_SemiBold, size: 18))
                    }
                    
                    HStack(spacing: 2){
                        Text("Heb je geen account?")
                            .font(.custom(Font.Poppins_Regular, size: 14))
                        NavigationLink {
                            RegisterView()
                                .navigationBarBackButtonHidden(true)
                        } label: {
                            Text("Aanmelden")
                                .font(.custom(Font.Poppins_Regular, size: 14))
                                .foregroundColor(Color("primary-color"))
                        }
                    }
                    .padding(.top)
                                    
                    Spacer()
                    
                }
                .alert(authViewModel.errorMessage, isPresented: $authViewModel.isError) {
                            Button("OK", role: .cancel) { }
                }
                .padding(.horizontal, 5)
                .navigationTitle("Login")
                .navigationBarHidden(true)
                
            case .loading:
                ProgressView()
            }
            
        }
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
