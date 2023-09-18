//
//  LoginView.swift
//  catchnl
//
//  Created by Fatih Toker on 12.09.2023.
//

import SwiftUI

struct ResetPasswordView: View {
    @StateObject var authViewModel = AuthViewModel()
    @State var emailIsSended = false

    var body: some View {
        NavigationView {
            switch authViewModel.state {
            case .nothing:
                VStack(alignment: .center){

                    Image("logo-dark")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 200)
                        .padding(.bottom)
                                

                    
                    InputCellView(hint: "Email",systemImageName: "envelope" , text: $authViewModel.email, state: .text)
                        .padding(.horizontal)
                    
                    Button {
                        authViewModel.state = .loading
                        authViewModel.resetPassword{ isSuccess in
                            if isSuccess {
                                emailIsSended = true
                                authViewModel.state = .nothing
                            } else {
                                print("error while sending email")
                                authViewModel.state = .nothing
                            }
                        }
                    } label: {
                        Text("Reset wachtwoord")
                            .modifier(ButtonModifier())
                            .font(.custom(Font.Poppins_SemiBold, size: 18))
                    }
            
                    Spacer()
                    
                }
                //.padding(.horizontal, 5)
                .padding()
                .alert(authViewModel.errorMessage, isPresented: $authViewModel.isError) {
                    Button("OK", role: .cancel) { }
                }
                .alert("De resetlink is naar uw e-mailadres verzonden.", isPresented: $emailIsSended) {
                    Button("OK", role: .cancel) { }
                }
                
            case .loading:
                ProgressView()
            }
            
            
        }
        
    }
}

struct ResetPasswordView_Previews: PreviewProvider {
    static var previews: some View {
        ResetPasswordView()
    }
}
